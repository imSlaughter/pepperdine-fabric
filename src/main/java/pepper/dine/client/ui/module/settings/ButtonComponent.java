package pepper.dine.client.ui.module.settings;

import net.minecraft.client.gui.DrawContext;
import pepper.dine.client.features.api.RunSetting;
import pepper.dine.client.ui.module.SettingComponent;

public class ButtonComponent extends SettingComponent {

    private final RunSetting setting;

    public ButtonComponent(RunSetting setting) {
        super(setting);
        this.setting = setting;
        setHeight(15.0f);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()), 0xFF353535);
        int textWidth = mc.textRenderer.getWidth(setting.getName());
        context.drawText(mc.textRenderer, setting.getName(), (int) (getX() + (getWidth() - textWidth) / 2), (int) getY() + 4, 0xFFFFFFFF, false);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight()) && setting.getValue() != null) {
            setting.getValue().run();
        }
    }
}
