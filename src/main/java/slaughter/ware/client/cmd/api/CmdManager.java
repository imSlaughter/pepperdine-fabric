package slaughter.ware.client.cmd.api;

import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import slaughter.ware.SlaughterWare;
import slaughter.ware.client.event.impl.EventCmd;
import slaughter.ware.client.utils.chat.ChatUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CmdManager {
    private final List<Cmd> cmds = new ArrayList<>();

    public CmdManager() {
        SlaughterWare.getInstance().getEventBus().register(this);
    }

    @Subscribe
    public void onCmd(EventCmd e) {
        if (e.isSend()) {
            String message = e.getText();

            if (message.startsWith("*")) {

                e.cancel();

                String messagexd = message.substring(1);
                String[] args = messagexd.split(",");
                boolean cmdFound = false;
                for (Cmd cmd : cmds) {
                    if (cmd.getName().equalsIgnoreCase(args[0])) {
                        try {
                            cmd.execute(args);
                            cmdFound = true;
                            break;
                        } catch (Exception ex) {
                            ChatUtil.addMess("Command not found: ");
                            cmdFound = true;
                            break;
                        }
                    }
                }

                if (!cmdFound) {
                    ChatUtil.addMess("Лох у тя ниче не получится.");
                }
            }
        }
    }
}
