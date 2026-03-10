package pepper.dine.client.features.implementations.movement;

import org.lwjgl.glfw.GLFW;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", GLFW.GLFW_KEY_V, ModuleCategory.MOVEMENT);
    }
}
