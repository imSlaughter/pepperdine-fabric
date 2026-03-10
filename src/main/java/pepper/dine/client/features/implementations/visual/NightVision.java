package pepper.dine.client.features.implementations.visual;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;

public class NightVision extends Module {

    public NightVision() {
        super("NightVision", GLFW.GLFW_KEY_UNKNOWN, ModuleCategory.VISUAL);
    }

    @Override
    public void onUpdate() {
        if (mc().player == null) {
            return;
        }

        StatusEffectInstance current = mc().player.getStatusEffect(StatusEffects.NIGHT_VISION);
        if (current == null || current.getDuration() < 220) {
            mc().player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
        }
    }

    @Override
    protected void onDisable() {
        if (mc().player != null) {
            mc().player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }
}
