package com.github.sialsialsial.xainocheat.mixin;

import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public class MixinGameSettings {

    // Change gamma settings
    //@Inject(method = "loadOptions", at = @At("TAIL"))
    //private void onLoadOptions(CallbackInfo ci) {
        // Change gamma to max
        // In Minecraft 1.8.9 variable gammaSetting have a float type.
        //((GameSettings)(Object)this).gammaSetting = 100.0F; 
    }
}
