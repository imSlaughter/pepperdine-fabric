package slaughter.ware.mixin.game;

import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slaughter.ware.SlaughterWare;
import slaughter.ware.client.event.impl.EventCmd;

@Mixin(ChatScreen.class)
public class MixinChatScreen {

    @Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
    public void sendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
        EventCmd eventCmd = new EventCmd(chatText, true);
        SlaughterWare main = SlaughterWare.getInstance();
        if (main != null) {
            main.postEvent(eventCmd);
        }

        if (chatText.startsWith("`")) {
            ci.cancel();
        }
    }
}
