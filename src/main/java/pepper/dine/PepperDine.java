package pepper.dine;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import pepper.dine.client.command.CommandManager;
import pepper.dine.client.config.ConfigManager;
import pepper.dine.client.event.api.Event;
import pepper.dine.client.modules.ModuleRepository;
import pepper.dine.client.modules.api.Module;

@Getter
public class PepperDine implements ModInitializer {

    @Getter
    public static PepperDine instance;
    private EventBus eventBus;
    private ModuleRepository moduleRepository;
    private CommandManager commandManager;
    private ConfigManager configManager;

    @Override
    public void onInitialize() {
        instance = this;
        eventBus = new EventBus();

        moduleRepository = new ModuleRepository();
        moduleRepository.init();
        eventBus.register(moduleRepository);

        commandManager = new CommandManager();
        configManager = new ConfigManager();
        configManager.load();
    }

    public void registerModule(Module module) {
        moduleRepository.register(module);
    }

    public void postEvent(Event event) {
        if (eventBus != null) {
            eventBus.post(event);
        }
    }

    public void requestConfigSave() {
        if (configManager != null) {
            configManager.requestSave();
        }
    }
}
