package pepper.dine.client.ui.module.settings;

import net.minecraft.client.gui.DrawContext;
import pepper.dine.client.features.api.BooleanSetting;
import pepper.dine.client.ui.module.SettingComponent;

public class BooleanComponent extends SettingComponent {

    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting) {
        super(setting);
        this.setting = setting;
        setHeight(15.0f);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()), 0xFF2B2B2B);
        context.drawText(mc.textRenderer, setting.getName(), (int) getX() + 4, (int) getY() + 4, 0xFFFFFFFF, false);
        String value = setting.getValue() ? "ON" : "OFF";
        int color = setting.getValue() ? 0xFF7CFF7C : 0xFFFF7C7C;
        int width = mc.textRenderer.getWidth(value);
        context.drawText(mc.textRenderer, value, (int) (getX() + getWidth()) - width - 4, (int) getY() + 4, color, false);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())) {
            setting.toggle();
        }
    }
}
