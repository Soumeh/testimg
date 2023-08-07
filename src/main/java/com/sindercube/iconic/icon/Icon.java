package com.sindercube.iconic.icon;

import net.minecraft.text.MutableText;
import net.minecraft.util.Identifier;

import static com.sindercube.iconic.Iconic.MODID;

public class Icon {
    public static MutableText get(Identifier id) {
        return MutableText.of(new IconTextContent(id));
    }
    public static MutableText get(String key) {
        if (key.contains(":")) return get(new Identifier(key));
        return get(new Identifier(MODID, key));
    }
    public static MutableText get(String namespace, String key) {
        return get(new Identifier(namespace, key));
    }
}
