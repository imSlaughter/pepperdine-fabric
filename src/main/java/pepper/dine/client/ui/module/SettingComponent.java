package pepper.dine.client.ui.module;

import pepper.dine.client.features.api.Setting;
import pepper.dine.client.ui.UIComponent;

public abstract class SettingComponent extends UIComponent {

    private final Setting<?> setting;
    private float visibleAnimation = 1.0f;

    protected SettingComponent(Setting<?> setting) {
        this.setting = setting;
    }

    public Setting<?> getSetting() {
        return setting;
    }

    public void updateVisible() {
        float target = setting.isVisible() ? 1.0f : 0.0f;
        visibleAnimation += (target - visibleAnimation) * 0.3f;
        if (Math.abs(target - visibleAnimation) < 0.01f) {
            visibleAnimation = target;
        }
    }

    public float getVisibleAnimation() {
        return visibleAnimation;
    }
}
