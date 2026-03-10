package pepper.dine.client.event.impl;

import lombok.Getter;
import pepper.dine.client.event.api.Event;

@Getter
public class EventKey extends Event {

    private final int key;

    public EventKey(int key) {
        this.key = key;
    }
}
