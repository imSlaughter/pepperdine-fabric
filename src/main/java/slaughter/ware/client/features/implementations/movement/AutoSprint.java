package slaughter.ware.client.features.implementations.movement;

import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

public class AutoSprint extends Module {

    public AutoSprint() {
        super("AutoSprint", GLFW.GLFW_KEY_G, ModuleCategory.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (fullNullCheck()) return;
        boolean shouldSprint =
                mc().options.forwardKey.isPressed()
                        && !mc().player.isSneaking()
                        && !mc().player.horizontalCollision;

        mc().options.sprintKey.setPressed(shouldSprint);
    }

    @Override
    protected void onDisable() {
        if (!fullNullCheck()) {
            mc().options.sprintKey.setPressed(false);
            mc().player.setSprinting(false);
        }
    }

}
