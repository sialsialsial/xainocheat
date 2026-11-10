package com.github.sialsialsial.xainocheat.mixin;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public class MixinGuiMainMenu {

    @Inject(method = "loadOptions", at = @At("TAIL"))
    public void onLoadOptions(CallbackInfo ci) {
        GameSettings settings = (GameSettings)(Object)this;
        // Set the gamma setting to maximum
        settings.gammaSetting = 100.0F;
    }
}
