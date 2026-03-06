package slaughter.ware.client.modules.api;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.utils.Minecraft.IMinecraft;

@Getter
public class Module implements IMinecraft {

    private final String name;
    private int key;
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

    public void onRender2D(DrawContext context, RenderTickCounter tickCounter) {
    }

    protected MinecraftClient mc() {
        return IMinecraft.mc();
    }

    public void setKey(int key) {
        this.key = key;
    }

    protected void setEnabledState(boolean enabled) {
        this.enabled = enabled;
    }
}
