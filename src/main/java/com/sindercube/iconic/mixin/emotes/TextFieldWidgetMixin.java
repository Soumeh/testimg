package com.sindercube.iconic.mixin.emotes;


import com.sindercube.iconic.emote.EmoteStorage;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.regex.Matcher;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin {
    @Shadow
    private int selectionEnd;
    @Shadow
    private int selectionStart;
    @Shadow
    public abstract void eraseCharacters(int c);
    @Shadow
    public abstract String getText();
    @Shadow
    protected abstract int getCursorPosWithOffset(int o);
    @Shadow
    public abstract void write(String t);

    @Inject(method = "charTyped(CI)Z", at = @At("RETURN"))
    private void replaceEmotes(CallbackInfoReturnable<Boolean> c) {
        String text = getText();
        if (text.isBlank()) return;

        int cursor = getCursorPosWithOffset(1);
        String selected = text.substring(0, cursor);
        if (!selected.equals(text)) cursor -= 1;
        if (text.charAt(cursor-1) != ':') return;

        Matcher matcher = EmoteStorage.EMOTE_PATTERN.matcher(selected);
        String emote = "";
        while (matcher.find()) emote = matcher.group(1);
        Text trueEmote = EmoteStorage.INSTANCE.get(emote);
        if (trueEmote == null) return;

        eraseCharacters(-emote.length()-2);
        selectionEnd = selectionStart;
        write(trueEmote.getString());
    }
}