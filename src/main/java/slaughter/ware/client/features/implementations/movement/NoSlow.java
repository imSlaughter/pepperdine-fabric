package slaughter.ware.client.features.implementations.movement;

import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", GLFW.GLFW_KEY_V, ModuleCategory.MOVEMENT);
    }
}
