package com.sindercube.iconic.textComponents.defaultComponents;

import com.google.gson.*;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NBTComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return "nbt";
    }
    @Override
    public Class<?> getContent() {
        return NbtTextContent.class;
    }
    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        NbtDataSource nbtDataSource;
        if (object.has("block")) {
            nbtDataSource = new BlockNbtDataSource(object.get("block").getAsString());
        } else if (object.has("entity")) {
            nbtDataSource = new EntityNbtDataSource(object.get("entity").getAsString());
        } else if (object.has("storage")) {
            nbtDataSource = new StorageNbtDataSource(new Identifier(object.get("storage").getAsString()));
        } else {
            throw new IllegalArgumentException(Text.translatable("error.text_component.deserialize", data).getString());
        }

        boolean interpret = object.get("interpret").getAsBoolean();

        Optional<Text> separator = Optional.empty();
        if (object.has("separator")) {
            separator = Optional.of(SERIALIZER.deserialize(object.get("separator"), type, context));
        }

        return new NbtTextContent(data.getAsString(), interpret, separator, nbtDataSource);
    }
    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        JsonObject jsonData = new JsonObject();
        NbtTextContent nbtTextContent = (NbtTextContent)content;
        jsonData.addProperty("nbt", nbtTextContent.getPath());
        jsonData.addProperty("interpret", nbtTextContent.shouldInterpret());

        Optional<Text> separator = nbtTextContent.getSeparator();
        if (separator.isPresent()) {
            JsonElement trueSeparator = SERIALIZER.serialize(separator.get(), separator.getClass(), context);
            jsonData.add("separator", trueSeparator);
        }

        NbtDataSource nbtDataSource = nbtTextContent.getDataSource();
        if (nbtDataSource instanceof BlockNbtDataSource blockNbtDataSource) {
            jsonData.addProperty("block", blockNbtDataSource.rawPos());
        } else if (nbtDataSource instanceof EntityNbtDataSource entityNbtDataSource) {
            jsonData.addProperty("entity", entityNbtDataSource.rawSelector());
        } else if (nbtDataSource instanceof StorageNbtDataSource storageNbtDataSource) {
            jsonData.addProperty("storage", storageNbtDataSource.id().toString());
        } else {
            throw new IllegalArgumentException(Text.translatable("error.text_component.serialize", nbtDataSource.toString()).getString());
        }
        return jsonData;
    }
}
