package com.sindercube.iconic.textComponents;

import net.minecraft.text.Text;
import net.minecraft.text.TextContent;

import java.util.HashMap;
import java.util.Map;

public class TextComponentStorage {
    public static TextComponentStorage INSTANCE = new TextComponentStorage();
    public Map<String, BaseTextComponent> textComponentsIds;
    public Map<Class<TextContent>, BaseTextComponent> textComponentContents;
    public TextComponentStorage() {
        textComponentsIds = new HashMap<>();
        textComponentContents = new HashMap<>();
    }
    public void addComponent(BaseTextComponent textComponent) {
        textComponentsIds.put(textComponent.getId(), textComponent);
        //noinspection unchecked
        textComponentContents.put((Class<TextContent>)textComponent.getContent(), textComponent);
    }

    //public MutableText get(String id, Object ... args) {
    //    return textComponentsIds.get(id).get(args);
    //}
}
