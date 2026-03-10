package pepper.dine.mixin.render;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pepper.dine.client.features.implementations.visual.Removals;

@Mixin(BossBarHud.class)
public class MixinBossBarHud {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void slaughterware$cancelBossBar(DrawContext context, CallbackInfo ci) {
        Removals removals = Removals.getInstance();
        if (removals != null && removals.isBossBar()) {
            ci.cancel();
        }
    }
}
