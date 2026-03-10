package pepper.dine.client.utils.render.color;

import lombok.experimental.UtilityClass;
import net.minecraft.util.math.MathHelper;
import pepper.dine.client.utils.Minecraft.IMinecraft;

import java.awt.*;

@UtilityClass
public class ColorUtil implements IMinecraft {
    public Color gradient(Color color1, Color color2, float amount) {
        amount = MathHelper.clamp(amount, 0, 1);
        int r = MathHelper.lerp(amount, color1.getRed(), color2.getRed());
        int g = MathHelper.lerp(amount, color1.getGreen(), color2.getGreen());
        int b = MathHelper.lerp(amount, color1.getBlue(), color2.getBlue());
        int a = MathHelper.lerp(amount, color1.getAlpha(), color2.getAlpha());

        return new Color(r, g, b, a);

    }

}
