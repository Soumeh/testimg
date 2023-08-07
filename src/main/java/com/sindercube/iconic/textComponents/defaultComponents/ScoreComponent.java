package com.sindercube.iconic.textComponents.defaultComponents;

import com.google.gson.*;
import com.sindercube.iconic.textComponents.BaseTextComponent;
import net.minecraft.text.MutableText;
import net.minecraft.text.ScoreTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ScoreComponent extends BaseTextComponent {
    @Override
    public String getId() {
        return "score";
    }

    @Override
    public Class<?> getContent() {
        return ScoreTextContent.class;
    }

    @Override
    public TextContent deserialize(JsonElement data, JsonObject object, Type type, JsonDeserializationContext context) {
        JsonObject scoreObject = data.getAsJsonObject();
        if (!scoreObject.has("name") || !scoreObject.has("objective")) {
            throw new JsonParseException(Text.translatable("error.text_component.score").getString());
        }
        String name = scoreObject.get("name").getAsString();
        String objective = scoreObject.get("objective").getAsString();
        return new ScoreTextContent(name, objective);
    }
    @Override
    public JsonObject serialize(TextContent content, Type type, JsonSerializationContext context) {
        JsonObject jsonData = new JsonObject();
        ScoreTextContent scoreTextContent = (ScoreTextContent)content;
        JsonObject scoreData = new JsonObject();
        scoreData.addProperty("name", scoreTextContent.getName());
        scoreData.addProperty("objective", scoreTextContent.getObjective());
        jsonData.add("score", scoreData);
        return jsonData;
    }
}
