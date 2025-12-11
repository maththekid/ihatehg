package me.viniciusroger.ihatehg.mixins;

import me.viniciusroger.ihatehg.events.PreUpdateEvent;
import me.viniciusroger.ihatehg.events.SendMessageEvent;
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

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void onSendChatMessage(String message, CallbackInfo ci) {
        SendMessageEvent event = new SendMessageEvent(message);

        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}
