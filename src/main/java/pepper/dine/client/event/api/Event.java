package pepper.dine.client.event.api;

import lombok.Getter;
import pepper.dine.PepperDine;

@Getter
public class Event {

    private boolean cancelled;

    public void cancel() {
        cancelled = true;
    }

    public void resume() {
        cancelled = false;
    }

    public void call() {
        PepperDine main = PepperDine.getInstance();
        if (main != null && main.getEventBus() != null) {
            main.getEventBus().post(this);
        }
    }
}
