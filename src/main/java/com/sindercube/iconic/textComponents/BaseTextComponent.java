package com.sindercube.iconic.textComponents;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.sindercube.iconic.textComponents.defaultComponents.TextComponent;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

import java.lang.reflect.Type;
import java.util.List;

public abstract class BaseTextComponent {
    public static final Text.Serializer SERIALIZER = new Text.Serializer();
    public abstract String getId();
    public abstract Class<?> getContent();
    public abstract TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context);
    public abstract JsonObject serialize(TextContent content, Type type, JsonSerializationContext context);
}
