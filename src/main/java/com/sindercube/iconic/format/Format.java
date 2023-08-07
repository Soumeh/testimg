package com.sindercube.iconic.format;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class Format {
    public static MutableText text(Text text) {
        return MutableText.of(new FormatTextContent(text));
    }
}
