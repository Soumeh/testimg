package com.sindercube.iconic.textComponents;

import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.TextContent;
import org.jetbrains.annotations.Nullable;

public abstract class BaseTextContent implements TextContent {
    public abstract MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth);
    public abstract boolean equals(Object obj);
    public abstract int hashCode();
    public abstract String toString();
}
