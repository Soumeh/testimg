package com.sindercube.iconic.textComponents.defaultComponents;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.MutableText;
import net.minecraft.text.SelectorTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SelectorComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return null;
    }
    @Override
    public Class<?> getContent() {
        return SelectorTextContent.class;
    }
    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        Optional<Text> separator = Optional.empty();
        if (object.has("separator")) {
            separator = Optional.of(SERIALIZER.deserialize(object.get("separator"), type, context));
        }
        return new SelectorTextContent(data.getAsString(), separator);
    }
    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        JsonObject jsonData = new JsonObject();
        SelectorTextContent selectorTextContent = (SelectorTextContent)content;
        jsonData.addProperty("selector", selectorTextContent.getPattern());
        Optional<Text> separator = selectorTextContent.getSeparator();
        if (separator.isPresent()) {
            JsonElement trueSeparator = SERIALIZER.serialize(separator.get(), separator.getClass(), context);
            jsonData.add("separator", trueSeparator);
        }
        return jsonData;
    }
}
