package slaughter.ware.client.ui.module.settings;

import net.minecraft.client.gui.DrawContext;
import slaughter.ware.client.features.api.SliderSetting;
import slaughter.ware.client.ui.module.SettingComponent;

public class SliderComponent extends SettingComponent {

    private final SliderSetting setting;
    private boolean dragging;

    public SliderComponent(SliderSetting setting) {
        super(setting);
        this.setting = setting;
        setHeight(18.0f);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + getHeight()), 0xFF2B2B2B);
        String valueText = String.format("%.1f", setting.getValue());
        context.drawText(mc.textRenderer, setting.getName(), (int) getX() + 4, (int) getY() + 3, 0xFFFFFFFF, false);
        context.drawText(mc.textRenderer, valueText, (int) (getX() + getWidth()) - mc.textRenderer.getWidth(valueText) - 4, (int) getY() + 3, 0xFFFFFFFF, false);

        float sliderX = getX() + 4;
        float sliderY = getY() + getHeight() - 6;
        float sliderWidth = getWidth() - 8;
        context.fill((int) sliderX, (int) sliderY, (int) (sliderX + sliderWidth), (int) (sliderY + 3), 0xFF1B1B1B);

        float progress = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        float fillWidth = sliderWidth * progress;
        context.fill((int) sliderX, (int) sliderY, (int) (sliderX + fillWidth), (int) (sliderY + 3), 0xFFFFFFFF);

        if (dragging) {
            updateValue(mouseX, sliderX, sliderWidth);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())) {
            dragging = true;
            updateValue(mouseX, getX() + 4, getWidth() - 8);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
    }

    private void updateValue(double mouseX, float sliderX, float sliderWidth) {
        float progress = (float) ((mouseX - sliderX) / sliderWidth);
        progress = Math.max(0.0f, Math.min(1.0f, progress));
        float value = setting.getMin() + (setting.getMax() - setting.getMin()) * progress;
        float stepped = Math.round(value / setting.getStep()) * setting.getStep();
        setting.setValue(Math.max(setting.getMin(), Math.min(setting.getMax(), stepped)));
    }
}
