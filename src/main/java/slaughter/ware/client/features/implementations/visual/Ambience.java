package slaughter.ware.client.features.implementations.visual;

import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

public class Ambience extends Module {

    public Ambience() {
        super("Ambience", GLFW.GLFW_KEY_F, ModuleCategory.VISUAL);
    }

    public long getTime(long original) {
        if (mc().world == null || !isEnabled()) return original;
        return 13000L;
    }
}
