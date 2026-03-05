package slaughter.ware.client.features.implementations.visual;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

import java.awt.*;

public class Hud extends Module {

    public Hud() {
        super("HUD", GLFW.GLFW_KEY_K, ModuleCategory.VISUAL);
    }

    @Override
    public void onRender2D(DrawContext dc, RenderTickCounter tickCounter) {
        final String text = "SlaughterWare";
        final int x = 30;
        final int y = 20;
        final int paddingX = 8;
        final int paddingY = 5;

        int textWidth = mc().textRenderer.getWidth(text);
        int width = textWidth + paddingX * 2;
        int height = mc().textRenderer.fontHeight + paddingY * 2;

        dc.fill(x, y, x + width, y + height, new Color(15, 15, 15, 190).getRGB());
        dc.fill(x, y, x + width, y + 2, new Color(170, 20, 20, 220).getRGB());
        dc.drawTextWithShadow(mc().textRenderer, text, x + paddingX, y + paddingY, 0xFFFFFFFF);
    }
}
