package slaughter.ware;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import slaughter.ware.client.command.CommandManager;
import slaughter.ware.client.event.api.Event;
import slaughter.ware.client.modules.ModuleRepository;
import slaughter.ware.client.modules.api.Module;

@Getter
public class SlaughterWare implements ModInitializer {

    @Getter
    public static SlaughterWare instance;
    private EventBus eventBus;
    private ModuleRepository moduleRepository;
    private CommandManager commandManager;

    @Override
    public void onInitialize() {
        instance = this;
        eventBus = new EventBus();

        moduleRepository = new ModuleRepository();
        moduleRepository.init();
        eventBus.register(moduleRepository);

        commandManager = new CommandManager();
    }

    public void registerModule(Module module) {
        moduleRepository.register(module);
    }

    public void postEvent(Event event) {
        if (eventBus != null) {
            eventBus.post(event);
        }
    }
}
