package pepper.dine.client.utils.chat;

import lombok.experimental.UtilityClass;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import pepper.dine.client.utils.Minecraft.IMinecraft;
import pepper.dine.client.utils.render.color.ColorUtil;

import java.awt.*;
@UtilityClass
public class ChatUtil implements IMinecraft {

    public void addMess(String message) {
        MutableText gradientText = Text.literal("");
        String textToGradient = "PepperDine! ";

        Color startColor = new Color(90, 237, 117);
        Color endColor = new Color(166, 21, 218);

        for (int i = 0; i < textToGradient.length() - 1; i++) {
            float amount = (float) i / (textToGradient.length());


            Color gradientColor = ColorUtil.gradient(startColor, endColor, amount);
            int r = gradientColor.getRed();
            int g = gradientColor.getGreen();
            int b = gradientColor.getBlue();


            int colorCode = (r << 16) | (g << 8) | b;

            gradientText.append(Text.literal(String.valueOf(textToGradient.charAt(i))).setStyle(Style.EMPTY.withColor(colorCode)));
        }

        Text finalText = gradientText.append(Text.literal(" >>> ").formatted(Formatting.GRAY))
                .append(Text.literal(String.valueOf(message)).formatted(Formatting.WHITE));
        IMinecraft.mc().inGameHud.getChatHud().addMessage(finalText);
    }
}
