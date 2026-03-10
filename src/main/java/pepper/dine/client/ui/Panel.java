package pepper.dine.client.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;
import pepper.dine.PepperDine;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.modules.api.Module;
import pepper.dine.client.ui.module.ModuleComponent;

import java.util.ArrayList;
import java.util.List;

public class Panel extends UIComponent {

    private final ModuleCategory category;
    private final List<ModuleComponent> modules = new ArrayList<>();
    private float scroll;

    public Panel(ModuleCategory category) {
        this.category = category;
        for (Module module : PepperDine.getInstance().getModuleRepository().getModules()) {
            if (module.getCategory() == category) {
                modules.add(new ModuleComponent(module));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill((int) getX(), (int) getY(), (int) (getX() + getWidth()), (int) (getY() + 18), 0xFF2B2B2B);
        context.fill((int) getX(), (int) (getY() + 18), (int) (getX() + getWidth()), (int) (getY() + getHeight()), 0xFF1F1F1F);

        String title = pretty(category.name());
        int textWidth = mc.textRenderer.getWidth(title);
        context.drawText(mc.textRenderer, title, (int) (getX() + (getWidth() - textWidth) / 2), (int) getY() + 5, 0xFFFFFFFF, false);

        float currentY = getY() + 20 + scroll;
        for (ModuleComponent module : modules) {
            module.setX(getX() + 2);
            module.setY(currentY);
            module.setWidth(getWidth() - 4);
            module.render(context, mouseX, mouseY, delta);
            currentY += module.getHeight() + 2;
        }
    }

    @Override
    public void keyPressed(KeyInput keyInput) {
        for (ModuleComponent module : modules) {
            module.keyPressed(keyInput);
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (!hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())) {
            return;
        }
        for (ModuleComponent module : modules) {
            module.mouseClicked(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (ModuleComponent module : modules) {
            module.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())) {
            scroll += (float) (verticalAmount * 10.0);
            scroll = Math.min(0.0f, scroll);
        }
    }

    private String pretty(String value) {
        return value.charAt(0) + value.substring(1).toLowerCase();
    }
}
