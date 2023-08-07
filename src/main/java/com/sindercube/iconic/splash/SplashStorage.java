package com.sindercube.iconic.splash;

import com.google.common.collect.Iterables;
import com.sindercube.iconic.cache.SingleCache;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import org.apache.commons.lang3.time.DateUtils;

import java.time.LocalDate;
import java.util.*;

public class SplashStorage {
    public static SplashStorage INSTANCE = new SplashStorage();
    public SingleCache<Integer> totalWeight = new SingleCache<>(0);
    public SingleCache<List<SplashText>> splashes = new SingleCache<>(new ArrayList<>());
    public static final List<String> MODS = FabricLoader.getInstance().getAllMods().stream().map(mod -> mod.getMetadata().getId()).toList();
    public static SingleCache<Text> CACHE = new SingleCache<>();
    public Text random() {
        if (CACHE.value != null) return CACHE.value;

        List<SplashText> tempList = new ArrayList<>(this.splashes.value);
        Collections.shuffle(tempList);
        Iterator<SplashText> iterator = Iterables.cycle(tempList).iterator();

        int randomWeight = (int) ((Math.random() * (this.totalWeight.value - 1)) + 1);
        while (iterator.hasNext()) {
            SplashText splash = iterator.next();
            randomWeight -= splash.weight*10;

            if (randomWeight > splash.weight) continue;

            if (splash.date != null) if (!DateUtils.isSameDay(splash.date, new Date())) {
                iterator.remove();
                continue;
            }

            if (splash.mod != null) if (!MODS.contains(splash.mod)) {
                iterator.remove();
                continue;
            }

            CACHE.value = splash.text;
            return CACHE.value;
        }
        CACHE.value = Text.of("");
        return CACHE.value;
    }
    public void addSplash(SplashText splash) {
        System.out.println(splash);
        this.totalWeight.value += splash.weight;
        this.splashes.value.add(splash);
    }
}
