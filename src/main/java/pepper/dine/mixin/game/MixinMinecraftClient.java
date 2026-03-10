package pepper.dine.mixin.game;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pepper.dine.PepperDine;
import pepper.dine.client.event.impl.EventTick;
import pepper.dine.client.event.impl.EventUpdate;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        PepperDine main = PepperDine.getInstance();
        if (main != null) {
            main.postEvent(new EventTick());
            main.postEvent(new EventUpdate());
        }
    }
}
