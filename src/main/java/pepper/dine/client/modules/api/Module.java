package pepper.dine.client.modules.api;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import pepper.dine.PepperDine;
import pepper.dine.client.features.api.Setting;
import pepper.dine.client.modules.ModuleCategory;
import pepper.dine.client.utils.Minecraft.IMinecraft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Module implements IMinecraft {

    private final String name;
    private int key;
    private final ModuleCategory category;
    private final String description;
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean enabled;

    public Module(String name, int key, ModuleCategory category) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.description = "";
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
        if (PepperDine.getInstance() != null) {
            PepperDine.getInstance().requestConfigSave();
        }
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void onUpdate() {
    }

    public void onRender2D(DrawContext context, RenderTickCounter tickCounter) {
    }

    protected MinecraftClient mc() {
        return IMinecraft.mc();
    }

    public void setKey(int key) {
        this.key = key;
        if (PepperDine.getInstance() != null) {
            PepperDine.getInstance().requestConfigSave();
        }
    }

    protected void setEnabledState(boolean enabled) {
        this.enabled = enabled;
    }

    public void applySavedEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected void addSettings(Setting<?>... settings) {
        Collections.addAll(this.settings, settings);
    }
}
