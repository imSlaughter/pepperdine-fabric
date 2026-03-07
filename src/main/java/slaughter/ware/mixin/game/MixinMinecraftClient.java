package slaughter.ware.mixin.game;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slaughter.ware.SlaughterWare;
import slaughter.ware.client.event.impl.EventTick;
import slaughter.ware.client.event.impl.EventUpdate;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        SlaughterWare main = SlaughterWare.getInstance();
        if (main != null) {
            main.postEvent(new EventTick());
            main.postEvent(new EventUpdate());
        }
    }
}
