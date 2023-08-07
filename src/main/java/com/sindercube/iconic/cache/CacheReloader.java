package com.sindercube.iconic.cache;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static com.sindercube.iconic.Iconic.MODID;

public class CacheReloader implements SimpleSynchronousResourceReloadListener {
    public static final CacheReloader INSTANCE = new CacheReloader();
    public static List<Cache> CACHES = new ArrayList<>();
    @Override
    public Identifier getFabricId() {
        return new Identifier(MODID, "caches");
    }
    @Override
    public void reload(ResourceManager manager) {
        for (Cache cache : CACHES) cache.clear();
    }
}
