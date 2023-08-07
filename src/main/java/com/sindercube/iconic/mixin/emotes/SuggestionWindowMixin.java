package com.sindercube.iconic.mixin.emotes;

import com.mojang.brigadier.suggestion.Suggestion;
import com.sindercube.iconic.emote.EmoteStorage;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.regex.Matcher;

@Mixin(ChatInputSuggestor.SuggestionWindow.class)
public class SuggestionWindowMixin {
    @Shadow @Final
    ChatInputSuggestor field_21615;
    @Shadow
    private int selection;
    @Shadow @Final
    private List<Suggestion> suggestions;

    @Inject(method = "complete()V", at = @At("TAIL"))
    private void replaceEmotes(CallbackInfo ci) {
        TextFieldWidget textFieldWidget = this.field_21615.textField;
        Suggestion suggestion = this.suggestions.get(this.selection);

        Matcher matcher = EmoteStorage.EMOTE_PATTERN.matcher(suggestion.getText());
        if (!matcher.find()) return;
        Text trueEmote = EmoteStorage.INSTANCE.get(matcher.group(1));
        if (trueEmote == null) return;

        textFieldWidget.eraseCharacters(-suggestion.getText().length());
        textFieldWidget.setSelectionEnd(textFieldWidget.getCursor());
        textFieldWidget.write(trueEmote.getString());
    }
}
