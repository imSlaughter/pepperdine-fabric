package pepper.dine.client.features.implementations.visual;

import org.lwjgl.glfw.GLFW;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;
import pepper.dine.client.ui.ScreenClickGUI;

public final class ClickGuiModule extends Module {

    public ClickGuiModule() {
        super("ClickGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, ModuleCategory.VISUAL);
    }

    @Override
    protected void onEnable() {
        if (mc().currentScreen instanceof ScreenClickGUI) {
            setEnabledState(false);
            return;
        }

        mc().setScreen(new ScreenClickGUI());
        setEnabledState(false);
    }
}
