package slaughter.ware.client.modules.api;

import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.utils.Minecraft.IMinecraft;

@Getter
public class Module implements IMinecraft {

    private final String name;
    private final int key;
    private final ModuleCategory category;
    private final String description;
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
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    public void onUpdate() {
    }

    protected MinecraftClient mc() {
        return IMinecraft.mc();
    }
}
