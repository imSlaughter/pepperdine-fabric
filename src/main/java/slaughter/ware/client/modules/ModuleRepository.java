package slaughter.ware.client.modules;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.util.InputUtil;
import slaughter.ware.client.event.impl.EventRenderer2D;
import slaughter.ware.client.event.impl.EventUpdate;
import slaughter.ware.client.features.implementations.movement.AutoSprint;
import slaughter.ware.client.features.implementations.movement.Fly;
import slaughter.ware.client.features.implementations.movement.NoSlow;
import slaughter.ware.client.features.implementations.visual.Ambience;
import slaughter.ware.client.features.implementations.visual.Hud;
import slaughter.ware.client.modules.api.Module;
import slaughter.ware.client.utils.Minecraft.IMinecraft;

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

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (IMinecraft.mc().player == null || IMinecraft.mc().world == null) {
            return;
        }

        for (Module module : modules) {
            boolean pressed = InputUtil.isKeyPressed(IMinecraft.window(), module.getKey());
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
