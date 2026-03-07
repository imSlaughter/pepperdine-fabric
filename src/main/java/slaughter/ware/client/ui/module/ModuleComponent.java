package slaughter.ware.client.ui.module;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.features.api.BindSetting;
import slaughter.ware.client.features.api.BooleanSetting;
import slaughter.ware.client.features.api.ModeSetting;
import slaughter.ware.client.features.api.MultiBooleanSetting;
import slaughter.ware.client.features.api.RunSetting;
import slaughter.ware.client.features.api.Setting;
import slaughter.ware.client.features.api.SliderSetting;
import slaughter.ware.client.modules.api.Module;
import slaughter.ware.client.ui.module.settings.BindComponent;
import slaughter.ware.client.ui.module.settings.BooleanComponent;
import slaughter.ware.client.ui.module.settings.ButtonComponent;
import slaughter.ware.client.ui.module.settings.ModeComponent;
import slaughter.ware.client.ui.module.settings.MultiBooleanComponent;
import slaughter.ware.client.ui.module.settings.SliderComponent;

import java.util.ArrayList;
import java.util.List;

public class ModuleComponent extends ExpandableComponent {

    private final Module module;
    private final List<SettingComponent> settings = new ArrayList<>();
    private boolean binding;

    public ModuleComponent(Module module) {
        this.module = module;

        for (Setting<?> setting : module.getSettings()) {
            if (setting instanceof BooleanSetting booleanSetting) {
                settings.add(new BooleanComponent(booleanSetting));
            } else if (setting instanceof MultiBooleanSetting multiBooleanSetting) {
                settings.add(new MultiBooleanComponent(multiBooleanSetting));
            } else if (setting instanceof ModeSetting modeSetting) {
                settings.add(new ModeComponent(modeSetting));
            } else if (setting instanceof SliderSetting sliderSetting) {
                settings.add(new SliderComponent(sliderSetting));
            } else if (setting instanceof RunSetting runSetting) {
                settings.add(new ButtonComponent(runSetting));
            } else if (setting instanceof BindSetting bindSetting) {
                settings.add(new BindComponent(bindSetting));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        updateOpen();

        int bg = module.isEnabled() ? 0xFF373737 : 0xFF242424;
        if (hovered(mouseX, mouseY, getX(), getY(), getWidth(), getDefaultHeight())) {
            bg = module.isEnabled() ? 0xFF404040 : 0xFF2B2B2B;
        }

        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getDefaultHeight()), bg);
        context.drawText(mc.textRenderer, module.getName(), (int) getX() + 5, (int) getY() + 3, 0xFFFFFFFF, false);

        if (binding) {
            String bindText = "[" + keyName(module.getKey()) + "]";
            int bindWidth = mc.textRenderer.getWidth(bindText);
            context.drawText(mc.textRenderer, bindText, (int) (getX() + getWidth()) - bindWidth - 5, (int) getY() + 3, 0xFFFFFFFF, false);
        }

        float currentY = getY() + getDefaultHeight();
        if (getAnim() > 0.02f) {
            for (SettingComponent setting : settings) {
                setting.updateVisible();
                if (setting.getVisibleAnimation() <= 0.02f) {
                    continue;
                }

                setting.setX(getX() + 5);
                setting.setY(currentY + 2);
                setting.setWidth(getWidth() - 10);
                setting.setAlpha(getAlpha());
                setting.render(context, mouseX, mouseY, delta);
                currentY += setting.getHeight() + 2;
            }
        }
        setHeight(Math.max(getDefaultHeight(), currentY - getY()));
    }

    @Override
    public void keyPressed(KeyInput keyInput) {
        if (binding) {
            int code = keyInput.getKeycode();
            module.setKey(code == GLFW.GLFW_KEY_ESCAPE ? GLFW.GLFW_KEY_UNKNOWN : code);
            binding = false;
            return;
        }

        if (!isOpen()) {
            return;
        }

        for (SettingComponent setting : settings) {
            setting.keyPressed(keyInput);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (hovered(mouseX, mouseY, getX(), getY(), getWidth(), getDefaultHeight())) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1 && !settings.isEmpty()) {
                toggleOpen();
            } else if (button == 2) {
                binding = !binding;
            }
            return;
        }

        if (!isOpen()) {
            return;
        }

        for (SettingComponent setting : settings) {
            setting.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (SettingComponent setting : settings) {
            setting.mouseReleased(mouseX, mouseY, button);
        }
    }

    public Module getModule() {
        return module;
    }

    public float getDefaultHeight() {
        return 17.0f;
    }

    private String keyName(int key) {
        if (key == GLFW.GLFW_KEY_UNKNOWN) {
            return "NONE";
        }
        String glfwName = GLFW.glfwGetKeyName(key, 0);
        return glfwName != null ? glfwName.toUpperCase() : "KEY" + key;
    }
}
