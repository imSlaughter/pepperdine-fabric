package slaughter.ware;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import slaughter.ware.client.command.CommandManager;
import slaughter.ware.client.config.ConfigManager;
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
