package pepper.dine.mixin.render;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pepper.dine.client.features.implementations.visual.Removals;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "isGlowing", at = @At("HEAD"), cancellable = true)
    private void pepperdine$disableGlow(CallbackInfoReturnable<Boolean> cir) {
        Removals removals = Removals.getInstance();
        if (removals != null && removals.isGlowEffect()) {
            cir.setReturnValue(false);
        }
    }
}
