package slaughter.ware.client.features.implementations.visual;

import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;
import slaughter.ware.client.ui.ClickGuiScreen;

public final class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, ModuleCategory.VISUAL);
    }

    @Override
    protected void onEnable() {
        if (mc().currentScreen instanceof ClickGuiScreen) {
            setEnabledState(false);
            return;
        }

        mc().setScreen(new ClickGuiScreen());
        setEnabledState(false);
    }
}
