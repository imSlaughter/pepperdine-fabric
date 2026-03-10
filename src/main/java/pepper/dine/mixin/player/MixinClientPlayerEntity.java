package pepper.dine.mixin.player;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pepper.dine.PepperDine;
import pepper.dine.client.features.implementations.movement.NoSlow;
import pepper.dine.client.modules.ModuleRepository;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "applyMovementSpeedFactors", at = @At("HEAD"), cancellable = true, require = 0)
    private void noSlowApplyMove(Vec2f input, CallbackInfoReturnable<Vec2f> cir) {
        if (isNoSlowEnabled()) {
            cir.setReturnValue(input);
        }
    }

    @Redirect(
            method = "tickMovement",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"),
            require = 0
    )
    private boolean noSlowTickMove(ClientPlayerEntity player) {
        return isNoSlowEnabled() ? false : player.isUsingItem();
    }

    @Redirect(
            method = "canStartSprinting",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"),
            require = 0
    )
    private boolean noSlowCanStart(ClientPlayerEntity player) {
        return isNoSlowEnabled() ? false : player.isUsingItem();
    }

    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    private void onShouldSlowDown(CallbackInfoReturnable<Boolean> cir) {
        if (isNoSlowEnabled()) {
            cir.setReturnValue(false);
        }
    }

    private boolean isNoSlowEnabled() {
        PepperDine main = PepperDine.getInstance();
        if (main == null) {
            return false;
        }

        ModuleRepository repository = main.getModuleRepository();
        if (repository == null) {
            return false;
        }

        NoSlow noSlow = repository.getModule(NoSlow.class);
        return noSlow != null && noSlow.isEnabled();
    }
}
