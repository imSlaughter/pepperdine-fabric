package slaughter.ware.client.event.impl;

import lombok.Getter;
import slaughter.ware.client.event.api.Event;
import slaughter.ware.client.utils.Minecraft.IMinecraft;

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
