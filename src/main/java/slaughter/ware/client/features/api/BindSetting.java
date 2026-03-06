package slaughter.ware.client.features.api;

import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class BindSetting extends Setting<Integer> {
    public BindSetting(String name) {
        super(name);
        this.value = GLFW.GLFW_KEY_UNKNOWN;
    }

    @Override
    public BindSetting value(Integer value) {
        setValue(value);
        return this;
    }

    @Override
    public void setValue(Integer value) {
        if (sameValue(value)) return;
        super.setValue(value);
        runAction();
    }

    @Override
    public BindSetting setVisible(Supplier<Boolean> condition) {
        return (BindSetting) super.setVisible(condition);
    }

    @Override
    public BindSetting onAction(Runnable action) {
        return (BindSetting) super.onAction(action);
    }
}
