package slaughter.ware.mixin.render;

import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slaughter.ware.client.features.implementations.visual.Removals;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "tiltViewWhenHurt", at = @At("HEAD"), cancellable = true)
    private void slaughterware$cancelHurtCamera(MatrixStack matrices, float tickProgress, CallbackInfo ci) {
        Removals removals = Removals.getInstance();
        if (removals != null && removals.isHurtCamera()) {
            ci.cancel();
        }
    }
}
