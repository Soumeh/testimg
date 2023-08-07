package com.sindercube.iconic.format;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Map;

import static com.sindercube.iconic.Iconic.LOGGER;
import static com.sindercube.iconic.Iconic.MODID;

public class FormatLoader implements SimpleSynchronousResourceReloadListener {
    public static final FormatLoader INSTANCE = new FormatLoader();
    @Override
    public Identifier getFabricId() {
        return new Identifier(MODID, "formats");
    }

    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> formatFiles = manager.findResources("formats.json", p -> true);
        for (Identifier path : formatFiles.keySet()) {
            Resource file = formatFiles.get(path);
            JsonObject data;
            try {
                data = JsonParser.parseReader(file.getReader()).getAsJsonObject();
                if (!data.isJsonObject()) {
                    LOGGER.error(Text.translatable("error.format_loader.type", path).getString());
                    continue;
                }
            } catch (IOException ignored) {
                LOGGER.error(Text.translatable("error.format_loader.file", path).getString());
                continue;
            }
            for (String key: data.keySet()) {
                JsonElement value = data.get(key);
                Text format = Text.Serializer.fromJson(value);
                FormatStorage.formats.put(key, format);
            }
        }
    }
}
