package pepper.dine.client.ui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import pepper.dine.client.modules.ModuleCategory;

import java.util.ArrayList;
import java.util.List;

public class ScreenClickGUI extends Screen {

    private final List<Panel> panels = new ArrayList<>();

    public ScreenClickGUI() {
        super(Text.literal(""));
        for (ModuleCategory category : ModuleCategory.values()) {
            panels.add(new Panel(category));
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x78000000);

        float panelWidth = 120.0f;
        float gap = 5.0f;
        float totalWidth = panels.size() * panelWidth + (panels.size() - 1) * gap;
        float startX = (width - totalWidth) / 2.0f;
        float startY = 42.0f;
        float panelHeight = height - 84.0f;

        for (int i = 0; i < panels.size(); i++) {
            Panel panel = panels.get(i);
            panel.setX(startX + i * (panelWidth + gap));
            panel.setY(startY);
            panel.setWidth(panelWidth);
            panel.setHeight(panelHeight);
            panel.render(context, mouseX, mouseY, delta);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        for (Panel panel : panels) {
            panel.mouseClicked(click.x(), click.y(), click.button());
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean mouseReleased(Click click) {
        for (Panel panel : panels) {
            panel.mouseReleased(click.x(), click.y(), click.button());
        }
        return super.mouseReleased(click);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (Panel panel : panels) {
            panel.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean keyPressed(KeyInput keyInput) {
        if (keyInput.getKeycode() == GLFW.GLFW_KEY_ESCAPE || keyInput.getKeycode() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            close();
            return true;
        }

        for (Panel panel : panels) {
            panel.keyPressed(keyInput);
        }
        return super.keyPressed(keyInput);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
