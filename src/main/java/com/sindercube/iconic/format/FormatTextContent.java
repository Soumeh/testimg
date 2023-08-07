package com.sindercube.iconic.format;

import com.sindercube.iconic.textComponents.BaseTextContent;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class FormatTextContent extends BaseTextContent {
    public Text text;
    public FormatTextContent(Text text) {
        this.text = text;
    }
    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) {
        return null;
    }
    @Override
    public boolean equals(Object obj) {
        return false;
    }
    @Override
    public int hashCode() {
        return 0;
    }
    @Override
    public String toString() {
        return null;
    }
}
