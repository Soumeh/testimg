package com.sindercube.iconic.format;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.TextContent;

import java.lang.reflect.Type;

public class InheritTextContent extends BaseTextComponent {
    @Override
    public String getId() {
        return "inherit";
    }
    @Override
    public Class<?> getContent() {
        return InheritTextContent.class;
    }
    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        return new LiteralTextContent("");
    }
    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        return null;
    }
}
