package pepper.dine.client.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pepper.dine.client.event.api.Event;

@Getter
@AllArgsConstructor
public class EventCmd extends Event {
    private String text;
    private boolean send;
}
