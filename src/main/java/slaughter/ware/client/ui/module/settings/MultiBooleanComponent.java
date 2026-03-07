package slaughter.ware.client.ui.module.settings;

import net.minecraft.client.gui.DrawContext;
import slaughter.ware.client.features.api.BooleanSetting;
import slaughter.ware.client.features.api.MultiBooleanSetting;
import slaughter.ware.client.ui.module.ExpandableComponent;

public class MultiBooleanComponent extends ExpandableComponent.ExpandableSettingComponent {

    private final MultiBooleanSetting setting;

    public MultiBooleanComponent(MultiBooleanSetting setting) {
        super(setting);
        this.setting = setting;
        setHeight(15.0f);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        updateOpen();
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + 15), 0xFF2B2B2B);
        context.drawText(mc.textRenderer, setting.getName(), (int) getX() + 4, (int) getY() + 4, 0xFFFFFFFF, false);
        context.drawText(mc.textRenderer, "...", (int) (getX() + getWidth()) - 10, (int) getY() + 4, 0xFFFFFFFF, false);

        float currentY = getY() + 17;
        if (getOpenAnim() > 0.02f) {
            for (BooleanSetting value : setting.getValue()) {
                context.fill((int) getX() + 4, (int) currentY, (int) (getX() + getWidth() - 4), (int) (currentY + 13), 0xFF242424);
                context.drawText(mc.textRenderer, value.getName(), (int) getX() + 8, (int) currentY + 3, 0xFFFFFFFF, false);
                String state = value.getValue() ? "ON" : "OFF";
                int stateWidth = mc.textRenderer.getWidth(state);
                int color = value.getValue() ? 0xFF7CFF7C : 0xFFFF7C7C;
                context.drawText(mc.textRenderer, state, (int) (getX() + getWidth()) - stateWidth - 8, (int) currentY + 3, color, false);
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
        for (BooleanSetting value : setting.getValue()) {
            if (hovered(mouseX, mouseY, getX() + 4, currentY, getWidth() - 8, 13)) {
                value.toggle();
                return;
            }
            currentY += 15;
        }
    }
}
