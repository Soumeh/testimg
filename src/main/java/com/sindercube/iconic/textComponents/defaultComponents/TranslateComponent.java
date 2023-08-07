package com.sindercube.iconic.textComponents.defaultComponents;

import com.google.gson.*;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TranslateComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return "translate";
    }
    @Override
    public Class<?> getContent() {
        return TranslatableTextContent.class;
    }
    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        String string = data.getAsString();
        if (!object.has("with")) return new TranslatableTextContent(string);

        JsonArray rawObjects = object.getAsJsonArray("with");
        List<Object> objects = new ArrayList<>();
        for (JsonElement element : rawObjects) {
            objects.add(SERIALIZER.deserialize(element, type, context));
        }

        return new TranslatableTextContent(string, objects);
    }

    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        TranslatableTextContent translatableContent = (TranslatableTextContent)content;
        JsonObject jsonData = new JsonObject();

        jsonData.addProperty("translate", translatableContent.getKey());
        if (translatableContent.getArgs().length == 0) return jsonData;

        JsonArray objects = new JsonArray();
        for (Object object : translatableContent.getArgs()) {
            if (object instanceof Text) {
                objects.add(this.serialize((TextContent)object, object.getClass(), context));
            } else {
                objects.add(new JsonPrimitive(String.valueOf(object)));
            }
        }
        jsonData.add("with", objects);
        return jsonData;
    }
}
