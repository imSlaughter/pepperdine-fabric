package pepper.dine.client.utils.Minecraft;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.Window;

public interface IMinecraft {

    static MinecraftClient mc() {
        return MinecraftClient.getInstance();
    }

    static Tessellator tessellator() {
        return Tessellator.getInstance();
    }

    static RenderTickCounter renderTickCounter() {
        return mc().getRenderTickCounter();
    }

    static Window window() {
        return mc().getWindow();
    }

    default boolean fullNullCheck() {
        return mc() == null || mc().player == null || mc().world == null;
    }
}
