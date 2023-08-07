package com.sindercube.iconic.icon;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontManager;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.FontType;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;

import static com.sindercube.iconic.Iconic.MODID;

public class IconLoader implements SimpleSynchronousResourceReloadListener {
    public static final IconLoader INSTANCE = new IconLoader();
    @Override
    public Identifier getFabricId() {
        return new Identifier(MODID, "icons");
    }

    @Override
    public void reload(ResourceManager manager) {
        Map<String, List<ResourcePackIcon>> map = new HashMap<>();
        Map<Identifier, Resource> iconTextures = manager.findResources("textures/icon", path -> path.toString().endsWith(".png"));
        for (Identifier path : iconTextures.keySet()) {
            String namespace = path.getNamespace();
            String iconID = path.toString().split("textures/icon/")[1].split(".png")[0];
            int height = 9;
            try {
                height = ImageIO.read(iconTextures.get(path).getInputStream()).getHeight();
            } catch (IOException ignored) {}
            int index = namespace.hashCode() + iconID.hashCode();
            ResourcePackIcon icon = new ResourcePackIcon(iconID, path, 8, height, index);
            if (!map.containsKey(namespace)) map.put(namespace, new LinkedList<>());
            map.get(namespace).add(icon);
        }

        MinecraftClient client = MinecraftClient.getInstance();
        TextureManager textureManager = client.getTextureManager();
        FontManager fontManager = client.fontManager;

        for (Map.Entry<String, List<ResourcePackIcon>> entry : map.entrySet()) {
            String namespace = entry.getKey();
            FontStorage storage = new FontStorage(textureManager, new Identifier(namespace));
            List<Font> fonts = new ArrayList<>();
            for (var icon : entry.getValue()) fonts.add(icon.toFont(manager));
            storage.setFonts(fonts);
            fontManager.fontStorages.put(new Identifier(namespace, "icons"), storage);
        }
    }
    static class ResourcePackIcon {
        public String id;
        public String file;
        public Integer ascent;
        public Integer height;
        public char character;
        public ResourcePackIcon(String id, Identifier file, Integer ascent, Integer height, Integer index) {
            this.id = id;
            this.file = file.getNamespace() + ":" + file.getPath().split("textures/")[1];
            this.ascent = ascent;
            this.height = height;
            this.character = (char)Integer.parseUnsignedInt(Integer.toHexString(index), 16);
        }
        public Font toFont(ResourceManager manager) {
            JsonObject data = new JsonObject();
            data.addProperty("type", "bitmap");
            data.addProperty("ascent", this.ascent);
            data.addProperty("height", this.height);
            data.addProperty("file", this.file);

            JsonArray chars = new JsonArray();
            chars.add(this.character);
            data.add("chars", chars);
            return FontType.BITMAP.createLoader(data).load(manager);
        }
    }
}
