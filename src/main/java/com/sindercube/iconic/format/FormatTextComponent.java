package com.sindercube.iconic.format;

import com.google.gson.*;
import com.sindercube.iconic.icon.IconTextContent;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.TranslatableTextContent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormatTextComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return "inherit";
    }

    @Override
    public Class<?> getContent() {
        return FormatTextContent.class;
    }

    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        return new FormatTextContent(Text.empty());
    }

    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        JsonObject jsonData = new JsonObject();
        // TODO
        return jsonData;
    }
}
