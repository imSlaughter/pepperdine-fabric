package pepper.dine.mixin.game;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pepper.dine.PepperDine;

import java.util.List;

@Mixin(ChatScreen.class)
public class MixinChatScreen {

    @Shadow protected TextFieldWidget chatField;

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    public void sendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        PepperDine main = PepperDine.getInstance();
        if (main != null && main.getCommandManager() != null && main.getCommandManager().handleChatMessage(chatText)) {
            ci.cancel();
        }
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(KeyInput keyInput, CallbackInfoReturnable<Boolean> cir) {
        if (keyInput.getKeycode() != GLFW.GLFW_KEY_TAB) {
            return;
        }

        PepperDine main = PepperDine.getInstance();
        if (main == null || main.getCommandManager() == null) {
            return;
        }

        String current = chatField.getText();
        String completed = main.getCommandManager().complete(current);
        if (!completed.equals(current)) {
            chatField.setText(completed);
            chatField.setCursorToEnd(false);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PepperDine main = PepperDine.getInstance();
        if (main == null || main.getCommandManager() == null) {
            return;
        }

        String current = chatField.getText();
        if (!current.startsWith(main.getCommandManager().getPrefix())) {
            return;
        }

        String hint = main.getCommandManager().getHint(current);
        List<String> suggestions = main.getCommandManager().getSuggestions(current);
        if (hint == null && suggestions.isEmpty()) {
            return;
        }

        var textRenderer = net.minecraft.client.MinecraftClient.getInstance().textRenderer;
        int left = chatField.getX();
        int width = chatField.getWidth();
        int lineHeight = 12;
        int baseY = chatField.getY() - 2;

        if (hint != null) {
            baseY -= lineHeight;
            context.fill(left, baseY, left + width, baseY + lineHeight, 0xCC000000);
            context.drawText(textRenderer, hint, left + 4, baseY + 2, 0xFFA0A0A0, false);
        }

        int max = Math.min(5, suggestions.size());
        for (int i = 0; i < max; i++) {
            baseY -= lineHeight;
            String suggestion = suggestions.get(i);
            int background = i == 0 ? 0xE03A3A3A : 0xCC000000;
            int textColor = i == 0 ? 0xFFFFFF00 : 0xFFFFFFFF;
            context.fill(left, baseY, left + width, baseY + lineHeight, background);
            context.drawText(textRenderer, suggestion, left + 4, baseY + 2, textColor, false);
        }
    }
}
