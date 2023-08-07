package com.sindercube.iconic.mixin.emotes;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.sindercube.iconic.emote.EmoteStorage;
import com.sindercube.iconic.splash.SplashLoader;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin {
    @Shadow @Final
    public TextFieldWidget textField;
    @Shadow @Nullable
    private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow
    public abstract void show(boolean n);
    @Inject(method = "refresh()V", at = @At("TAIL"))
    private void suggestEmotes(CallbackInfo ci) {
        List<String> suggestions = EmoteStorage.INSTANCE.getSuggestions();
        if (suggestions.isEmpty()) return;

        String text = this.textField.getText();
        if (text.isBlank() || text.startsWith("/")) return;

        int cursor = this.textField.getCursor();
        String selected = text.substring(0, cursor);
        int start = selected.lastIndexOf(':');
        if (start < 0 || selected.charAt(start) != ':') return;

        this.pendingSuggestions = CommandSource.suggestMatching(suggestions, new SuggestionsBuilder(selected, start));
        this.pendingSuggestions.thenRun(() -> {
            if (this.pendingSuggestions.isDone()) this.show(false);
        });
    }
}