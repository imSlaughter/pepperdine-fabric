package pepper.dine.client.modules;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.util.InputUtil;
import pepper.dine.client.event.impl.EventRenderer2D;
import pepper.dine.client.event.impl.EventUpdate;
import pepper.dine.client.features.implementations.combat.TriggerBot;
import pepper.dine.client.features.implementations.movement.AutoSprint;
import pepper.dine.client.features.implementations.movement.Fly;
import pepper.dine.client.features.implementations.movement.NoSlow;
import pepper.dine.client.features.implementations.visual.Ambience;
import pepper.dine.client.features.implementations.visual.ClickGuiModule;
import pepper.dine.client.features.implementations.visual.Hud;
import pepper.dine.client.features.implementations.visual.NightVision;
import pepper.dine.client.features.implementations.visual.Removals;
import pepper.dine.client.modules.api.Module;
import pepper.dine.client.utils.Minecraft.IMinecraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleRepository {

    private final List<Module> modules = new ArrayList<>();
    private final Map<Integer, Boolean> keyStates = new HashMap<>();

    public void init() {
        register(new AutoSprint());
        register(new Ambience());
        register(new Fly());
        register(new NoSlow());
        register(new Hud());
        register(new NightVision());
        register(new Removals());
        register(new ClickGuiModule());
        register(new TriggerBot());
    }

    public void register(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        for (Module module : modules) {
            if (moduleClass.isInstance(module)) {
                return moduleClass.cast(module);
            }
        }
        return null;
    }

    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (IMinecraft.mc().player == null || IMinecraft.mc().world == null) {
            return;
        }

        boolean screenOpen = IMinecraft.mc().currentScreen != null;

        for (Module module : modules) {
            boolean pressed = !screenOpen && module.getKey() != -1 && InputUtil.isKeyPressed(IMinecraft.window(), module.getKey());
            boolean wasPressed = keyStates.getOrDefault(module.getKey(), false);
            if (pressed && !wasPressed) {
                module.toggle();
            }
            keyStates.put(module.getKey(), pressed);

            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }

    @Subscribe
    public void onRender2D(EventRenderer2D event) {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onRender2D(event.getContext(), event.getCounter());
            }
        }
    }
}
