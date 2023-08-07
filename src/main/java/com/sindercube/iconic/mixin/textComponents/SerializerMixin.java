package com.sindercube.iconic.mixin.textComponents;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import com.sindercube.iconic.textComponents.TextComponentStorage;
import com.sindercube.iconic.textComponents.defaultComponents.TextComponent;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Mixin(Text.Serializer.class)
public class SerializerMixin {
    @Shadow
    private void addStyle(Style s, JsonObject j, JsonSerializationContext c) {}
    @Unique
    public final TextComponentStorage TCS = TextComponentStorage.INSTANCE;

    /**
     * @author Sindercube
     * @reason No other (reasonable) way to add text components
     */
    @Overwrite
    public MutableText deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        MutableText text;
        if (jsonElement.isJsonPrimitive()) {
            return Text.literal(jsonElement.getAsString());
        } else if (jsonElement.isJsonArray()) {
            text = Text.literal("");
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                MutableText part = this.deserialize(element, type, jsonDeserializationContext);
                text.append(part);
            }
            return text;
        } else if (jsonElement.isJsonObject()) {
            text = deserializeFromObject(jsonElement, type, jsonDeserializationContext);
            if (text.getStyle().isEmpty()) text.setStyle(new Style.Serializer().deserialize(jsonElement, type, jsonDeserializationContext));
            return text;
        } else {
            throw new JsonParseException(Text.translatable("error.text_component.deserialize", jsonElement).getString());
        }
    }
    @Unique
    public MutableText deserializeFromObject(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject object = jsonElement.getAsJsonObject();
        MutableText text = null;
        boolean found = false;
        for (String key : object.keySet()) {
            if (TCS.textComponentsIds.containsKey(key)) {
                BaseTextComponent component = TCS.textComponentsIds.get(key);
                JsonElement data = object.get(component.getId());
                TextContent content = component.deserialize(data, object, type, jsonDeserializationContext);
                text = MutableText.of(content);
                found = true;
                break;
            }
        }
        if (!found) throw new JsonParseException(Text.translatable("error.text_component.deserialize", jsonElement).getString());
        if (object.has("extra")) {
            for (JsonElement element : object.get("extra").getAsJsonArray()) {
                text.append(this.deserialize(element, type, jsonDeserializationContext));
            }
        }
        if (object.has("format")) {
            String format = object.get("format").getAsString();
        }
        return text;
    }

    /**
     * @author Sindercube
     * @reason No other (reasonable) way to add text components
     */
    @Overwrite
    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
        if (object instanceof String) {
            return new JsonPrimitive((String)object);
        }
        if (object instanceof List) {
            JsonArray texts = new JsonArray();
            for (Object newObject : (List<?>)object) {
                texts.add(this.serialize(newObject, Text.class, jsonSerializationContext));
            }
            return texts;
        }
        if (object instanceof Text) {
            return serializeFromText((Text)object, type, jsonSerializationContext);
        }
        return null;
    }
    @Unique
    public JsonObject serializeFromText(Text text, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json;
        TextContent textContent = text.getContent();
        Class<? extends TextContent> textClass = textContent.getClass();

        if (TCS.textComponentContents.containsKey(textClass)) {
            json = TCS.textComponentContents.get(textClass).serialize(textContent, type, jsonSerializationContext);
        } else {
            json = new JsonObject();
            json.addProperty("text", "");
        }

        if (!text.getStyle().isEmpty()) {
            this.addStyle(text.getStyle(), json, jsonSerializationContext);
        }

        if (!text.getSiblings().isEmpty()) {
            JsonArray siblings = new JsonArray();
            for (Text siblingText : text.getSiblings()) {
                siblings.add(this.serialize(siblingText, Text.class, jsonSerializationContext));
            }
            json.add("extra", siblings);
        }

        return json;
    }
}
