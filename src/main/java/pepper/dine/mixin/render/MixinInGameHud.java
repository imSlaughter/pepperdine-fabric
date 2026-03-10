package pepper.dine.mixin.render;


import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pepper.dine.PepperDine;
import pepper.dine.client.event.impl.EventRenderer2D;
import pepper.dine.client.features.implementations.visual.Removals;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(method = "render", at = @At("RETURN"))
    public void render(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        PepperDine.getInstance().getEventBus().post(new EventRenderer2D(context, tickCounter));
    }

    @Inject(method = "renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V", at = @At("HEAD"), cancellable = true)
    private void pepperdine$cancelScoreboard(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        Removals removals = Removals.getInstance();
        if (removals != null && removals.isScoreboard()) {
            ci.cancel();
        }
    }
}
