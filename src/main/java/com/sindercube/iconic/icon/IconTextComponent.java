package com.sindercube.iconic.icon;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import com.sindercube.iconic.textComponents.defaultComponents.TextComponent;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static com.sindercube.iconic.Iconic.MODID;

public class IconTextComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return "icon";
    }
    @Override
    public Class<?> getContent() {
        return IconTextContent.class;
    }
    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        Identifier id;
        if (data.getAsString().contains(":")) {
            id = new Identifier(data.getAsString());
        } else {
            id = new Identifier(MODID, data.getAsString());
        }
        return new IconTextContent(id);
    }
    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        JsonObject jsonData = new JsonObject();
        IconTextContent iconContent = ((IconTextContent)content);
        jsonData.addProperty("icon", iconContent.namespace + ":" + iconContent.key);
        return jsonData;
    }
}
