package com.sindercube.iconic.icon;

import com.sindercube.iconic.textComponents.BaseTextContent;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class IconTextContent extends BaseTextContent {
    public final String namespace;
    public final String key;
    public IconTextContent(Identifier id) {
        this.namespace = id.getNamespace();
        this.key = id.getPath();
    }
    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) {
        int index = namespace.hashCode() + key.hashCode();
        String rawText = String.valueOf((char)index);
        Style iconStyle = Style.EMPTY.withFont(new Identifier(namespace, "icons"));
        return Text.literal(rawText).setStyle(iconStyle);
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof IconTextContent trueObject)) return false;
        if (!this.namespace.equals(trueObject.namespace)) return false;
        return this.key.equals(trueObject.key);
    }
    public int hashCode() {
        return key.hashCode() + namespace.hashCode();
    }
    public String toString() {
        return "icon{namespace='" + namespace + "', key='" + key + "'}";
    }
}
