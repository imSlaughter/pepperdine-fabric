package slaughter.ware.client.features.implementations.visual;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;
import slaughter.ware.client.event.impl.EventRenderer2D;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

import java.awt.*;

public class Hud extends Module {

    public Hud() {
        super("HUD", GLFW.GLFW_KEY_K, ModuleCategory.VISUAL);
    }

    @Subscribe
    public void onRenderer2D(EventRenderer2D event) {
        DrawContext dc = event.getContext();

        int x = 30, y = 20, width = 30, height = 20;

        dc.fill(x, y, width, height, new Color(25, 25, 25).getRGB());
        dc.drawTextWithShadow(mc().textRenderer, "SlaughterWare", x + 2, y + 1, 0xFFFFFFFF);
    }
}
