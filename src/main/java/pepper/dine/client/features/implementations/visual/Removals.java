package pepper.dine.client.features.implementations.visual;

import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;
import pepper.dine.client.features.api.BooleanSetting;
import pepper.dine.client.features.api.MultiBooleanSetting;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;

public class Removals extends Module {

    private static Removals instance;

    private final MultiBooleanSetting remove = new MultiBooleanSetting("Remove").value(
            new BooleanSetting("Fire overlay").value(false),
            new BooleanSetting("Hurt camera").value(false),
            new BooleanSetting("Inwall overlay").value(false),
            new BooleanSetting("Water overlay").value(false),
            new BooleanSetting("Scoreboard").value(false),
            new BooleanSetting("Glow effect").value(false),
            new BooleanSetting("Bad effects").value(false),
            new BooleanSetting("Boss bar").value(false),
            new BooleanSetting("Slowness FOV").value(false)
    );

    public Removals() {
        super("Removals", GLFW.GLFW_KEY_UNKNOWN, ModuleCategory.VISUAL);
        instance = this;
        addSettings(remove);
    }

    public static Removals getInstance() {
        return instance;
    }

    public boolean isFireOverlay() {
        return isEnabled() && remove.isEnabled("Fire overlay");
    }

    public boolean isHurtCamera() {
        return isEnabled() && remove.isEnabled("Hurt camera");
    }

    public boolean isInwallOverlay() {
        return isEnabled() && remove.isEnabled("Inwall overlay");
    }

    public boolean isWaterOverlay() {
        return isEnabled() && remove.isEnabled("Water overlay");
    }

    public boolean isScoreboard() {
        return isEnabled() && remove.isEnabled("Scoreboard");
    }

    public boolean isGlowEffect() {
        return isEnabled() && remove.isEnabled("Glow effect");
    }

    public boolean isBadEffects() {
        return isEnabled() && remove.isEnabled("Bad effects");
    }

    public boolean isBossBar() {
        return isEnabled() && remove.isEnabled("Boss bar");
    }

    public boolean isSlownessFov() {
        return isEnabled() && remove.isEnabled("Slowness FOV");
    }

    @Override
    public void onUpdate() {
        if (!isBadEffects() || mc().player == null) {
            return;
        }

        mc().player.removeStatusEffect(StatusEffects.BLINDNESS);
        mc().player.removeStatusEffect(StatusEffects.NAUSEA);
        mc().player.removeStatusEffect(StatusEffects.DARKNESS);
    }
}
