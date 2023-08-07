package com.sindercube.iconic.splash;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Unique;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.sindercube.iconic.Iconic.LOGGER;
import static com.sindercube.iconic.Iconic.MODID;

public class SplashLoader implements SimpleResourceReloadListener<Void> {
    public static final SplashLoader INSTANCE = new SplashLoader();
    public static final SplashStorage STORAGE = SplashStorage.INSTANCE;
    private static final String SPLASHES_PATH = "splashes.json";
    @Override
    public Identifier getFabricId() {
            return new Identifier(MODID, "splashes");
    }
    @Override
    public CompletableFuture<Void> load(ResourceManager manager, Profiler profiler, Executor executor) {

        Map<Identifier, Resource> textReader = manager.findResources("texts", p -> p.getPath().endsWith("/"+SPLASHES_PATH));

        for (Identifier identifier : textReader.keySet()) {
            try (BufferedReader fileReader = textReader.get(identifier).getReader()) {
                try {
                    addSplashes(JsonParser.parseReader(fileReader).getAsJsonArray());
                } catch (ClassCastException | ParseException e) {
                    LOGGER.error(String.format("Unable to load splash file: '%s', file must be an array containing splash data", identifier));
                }
            } catch (IOException ignored) {}
        }

        return CompletableFuture.supplyAsync(() -> null);
    }
    @Unique
    public void addSplashes(JsonArray array) throws ParseException {
        for (JsonElement element: array) {
            Text text = Text.empty();
            int weight = 1;
            String date = null;
            String mod = null;

            if (element.isJsonPrimitive()) {
                text = Text.literal(element.getAsString());
            } else if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (!object.has("text")) continue;
                text = Text.Serializer.fromJson(object.get("text"));

                if (object.has("weight")) weight = object.get("weight").getAsInt();
                if (object.has("date")) date = object.get("date").getAsString();
                if (object.has("mod")) mod = object.get("mod").getAsString();
            }

            SplashText splashText = new SplashText(text, weight, date, mod);
            STORAGE.addSplash(splashText);
        }
    }
    @Override
    public CompletableFuture<Void> apply(Void data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.completedFuture(null);
    }
}
