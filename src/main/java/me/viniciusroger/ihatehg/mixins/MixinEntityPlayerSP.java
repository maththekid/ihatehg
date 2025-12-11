package me.viniciusroger.ihatehg.mixins;

import me.viniciusroger.ihatehg.events.PreUpdateEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void onUpdate(CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new PreUpdateEvent());
    }
}
