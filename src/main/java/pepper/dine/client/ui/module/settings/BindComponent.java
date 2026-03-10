package pepper.dine.client.ui.module.settings;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;
import pepper.dine.client.features.api.BindSetting;
import pepper.dine.client.ui.module.SettingComponent;

public class BindComponent extends SettingComponent {

    private final BindSetting setting;
    private boolean binding;

    public BindComponent(BindSetting setting) {
        super(setting);
        this.setting = setting;
        setHeight(15.0f);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()), 0xFF2B2B2B);
        context.drawText(mc.textRenderer, setting.getName(), (int) getX() + 4, (int) getY() + 4, 0xFFFFFFFF, false);
        String text = binding ? "PRESS KEY" : keyName(setting.getValue());
        int width = mc.textRenderer.getWidth(text);
        context.drawText(mc.textRenderer, text, (int) (getX() + getWidth()) - width - 4, (int) getY() + 4, 0xFFFFFFFF, false);
    }

    @Override
    public void keyPressed(KeyInput keyInput) {
        if (binding) {
            int code = keyInput.getKeycode();
            setting.setValue(code == GLFW.GLFW_KEY_ESCAPE ? GLFW.GLFW_KEY_UNKNOWN : code);
            binding = false;
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())) {
            binding = !binding;
        }
    }

    private String keyName(int key) {
        if (key == GLFW.GLFW_KEY_UNKNOWN || key == -999) {
            return "NONE";
        }
        String glfwName = GLFW.glfwGetKeyName(key, 0);
        return glfwName != null ? glfwName.toUpperCase() : "KEY" + key;
    }
}
