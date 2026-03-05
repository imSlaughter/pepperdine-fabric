package slaughter.ware.client.event.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import slaughter.ware.client.event.api.Event;

@Getter
@AllArgsConstructor
public class EventCmd extends Event {
    private String text;
    private boolean send;
}
