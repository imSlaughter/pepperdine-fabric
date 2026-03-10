package pepper.dine.client.features.implementations.visual;

import org.lwjgl.glfw.GLFW;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;

public class Ambience extends Module {

    public Ambience() {
        super("Ambience", GLFW.GLFW_KEY_F, ModuleCategory.VISUAL);
    }

    public long getTime(long original) {
        if (mc().world == null || !isEnabled()) return original;
        return 13000L;
    }
}
