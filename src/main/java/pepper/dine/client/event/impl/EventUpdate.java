package pepper.dine.client.event.impl;

import lombok.Getter;
import pepper.dine.client.event.api.Event;
import pepper.dine.client.utils.Minecraft.IMinecraft;

@Getter
public class EventUpdate extends Event {

    private final boolean pre;
    private final int playerAge;

    public EventUpdate() {
        this(true);
    }

    public EventUpdate(boolean pre) {
        this.pre = pre;
        this.playerAge = IMinecraft.mc().player != null ? IMinecraft.mc().player.age : -1;
    }

    public boolean isPost() {
        return !pre;
    }

}
