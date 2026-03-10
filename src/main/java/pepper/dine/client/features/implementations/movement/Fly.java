package pepper.dine.client.features.implementations.movement;

import org.lwjgl.glfw.GLFW;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;

public class Fly extends Module {

    public Fly() {
        super("Fly", GLFW.GLFW_KEY_X, ModuleCategory.MOVEMENT);
    }

    @Override
    protected void onEnable() {
        if (fullNullCheck()) return;
        mc().player.getAbilities().allowFlying = true;
        mc().player.getAbilities().flying = true;
    }

    @Override
    public void onUpdate() {
        if (fullNullCheck()) return;
        mc().player.getAbilities().flying = true;
    }

    @Override
    protected void onDisable() {
        if (fullNullCheck()) return;
        mc().player.getAbilities().flying = false;
        if (!mc().player.isCreative() && !mc().player.isSpectator()) {
            mc().player.getAbilities().allowFlying = false;
        }
    }
}
