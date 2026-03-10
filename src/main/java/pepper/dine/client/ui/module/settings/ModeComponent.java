package pepper.dine.client.ui.module.settings;

import net.minecraft.client.gui.DrawContext;
import pepper.dine.client.features.api.ModeSetting;
import pepper.dine.client.ui.module.ExpandableComponent;

public class ModeComponent extends ExpandableComponent.ExpandableSettingComponent {

    private final ModeSetting setting;

    public ModeComponent(ModeSetting setting) {
        super(setting);
        this.setting = setting;
        setHeight(15.0f);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        updateOpen();
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + 15), 0xFF2B2B2B);
        context.drawText(mc.textRenderer, setting.getName(), (int) getX() + 4, (int) getY() + 4, 0xFFFFFFFF, false);
        String mode = setting.getValue();
        int modeWidth = mc.textRenderer.getWidth(mode);
        context.drawText(mc.textRenderer, mode, (int) (getX() + getWidth()) - modeWidth - 4, (int) getY() + 4, 0xFFFFFFFF, false);

        float currentY = getY() + 17;
        if (getOpenAnim() > 0.02f) {
            for (String value : setting.getModes()) {
                context.fill((int) getX() + 4, (int) currentY, (int) (getX() + getWidth() - 4), (int) (currentY + 13), setting.is(value) ? 0xFF3A3A3A : 0xFF242424);
                context.drawText(mc.textRenderer, value, (int) getX() + 8, (int) currentY + 3, 0xFFFFFFFF, false);
                currentY += 15;
            }
        }
        setHeight(currentY - getY());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (hovered(mouseX, mouseY, getX(), getY(), getWidth(), 15)) {
            toggleOpen();
            return;
        }

        if (!isOpen()) {
            return;
        }

        float currentY = getY() + 17;
        for (String value : setting.getModes()) {
            if (hovered(mouseX, mouseY, getX() + 4, currentY, getWidth() - 8, 13)) {
                setting.setValue(value);
                return;
            }
            currentY += 15;
        }
    }
}
