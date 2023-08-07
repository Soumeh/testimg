package com.sindercube.iconic.textComponents.defaultComponents;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class TextComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return "text";
    }
    @Override
    public Class<?> getContent() {
        return LiteralTextContent.class;
    }
    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        return new LiteralTextContent(data.getAsString());
    }
    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("text", ((LiteralTextContent)content).string());
        return jsonData;
    }
}
