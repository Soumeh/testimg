package com.sindercube.iconic.mixin.splashes;

import com.sindercube.iconic.splash.SplashStorage;
import com.sindercube.iconic.splash.SplashText;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.text.ParseException;
import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextMixin {
    @Unique
    private static SplashStorage STORAGE = SplashStorage.INSTANCE;
    @Inject(
        method = "prepare(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)Ljava/util/List;",
        at = @At("RETURN"),
        cancellable = true
    )
    private void replaceTexts(CallbackInfoReturnable<List<String>> cir) throws ParseException {
        for (String text : cir.getReturnValue()) {
            SplashText splashText = new SplashText(Text.literal(text), 1, null, null);
            STORAGE.addSplash(splashText);
        }
        cir.setReturnValue(List.of(""));
    }
}