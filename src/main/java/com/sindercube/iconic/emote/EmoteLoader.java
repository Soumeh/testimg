package com.sindercube.iconic.emote;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import static com.sindercube.iconic.Iconic.LOGGER;
import static com.sindercube.iconic.Iconic.MODID;

public class EmoteLoader implements SimpleSynchronousResourceReloadListener {
    public static final EmoteLoader INSTANCE = new EmoteLoader();
    public static final EmoteStorage STORAGE = EmoteStorage.INSTANCE;
    @Override
    public Identifier getFabricId() {
        return new Identifier(MODID, "emotes");
    }
    public static final String EMOTES_PATH = "emotes.json";
    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> emoteReader = manager.findResources("texts", p -> p.getPath().endsWith("/"+EMOTES_PATH));

        for (Identifier identifier : emoteReader.keySet()) {
            BufferedReader fileReader;
            try {
                fileReader = emoteReader.get(identifier).getReader();
            } catch (IOException e) {
                continue;
            }
            try {
                JsonObject map = JsonParser.parseReader(fileReader).getAsJsonObject();
                for (String name: map.keySet()) {
                    JsonElement element = map.get(name);
                    Text text = Text.Serializer.fromJson(element);
                    if (!name.isBlank() && text != null) STORAGE.addEmote(name, text);
                }
            } catch (ClassCastException e) {
                LOGGER.error(String.format("Unable to load splash file: '%s', file must be an object with JSON Text elements mapped to strings", identifier));
            }
        }
    }
}
