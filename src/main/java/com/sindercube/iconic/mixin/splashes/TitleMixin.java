package com.sindercube.iconic.mixin.splashes;

import com.sindercube.iconic.splash.SplashLoader;
import com.sindercube.iconic.splash.SplashStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(TitleScreen.class)
public class TitleMixin {
    @Unique
    public Color defaultColor = new Color(255, 255, 0);
    @Unique
    public Text textSplashText;
    @Inject(method = "init()V", at = @At("HEAD"))
    public void addSplashText(CallbackInfo c) {
        textSplashText = SplashStorage.INSTANCE.random();
    }
    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Ljava/lang/String;)I"))
    private String setLength(String t) {
        return textSplashText.getString();
    }
    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;IIF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawCenteredText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    private void drawText(MatrixStack matrices, TextRenderer renderer, String s, int x, int y, int c) {
        TitleScreen.drawCenteredText(matrices, renderer, textSplashText.getString(), x, y, defaultColor.getRGB()); // 0xFFFF00
    }
}
