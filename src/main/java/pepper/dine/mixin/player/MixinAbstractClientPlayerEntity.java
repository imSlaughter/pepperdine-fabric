package pepper.dine.mixin.player;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pepper.dine.client.features.implementations.visual.Removals;

@Mixin(AbstractClientPlayerEntity.class)
public class MixinAbstractClientPlayerEntity {

    @Inject(method = "getFovMultiplier", at = @At("HEAD"), cancellable = true, require = 0)
    private void slaughterware$removeFovEffect(CallbackInfoReturnable<Float> cir) {
        Removals removals = Removals.getInstance();
        if (removals != null && removals.isSlownessFov()) {
            cir.setReturnValue(1.0f);
        }
    }
}
