package slaughter.ware.client.ui.module;

import slaughter.ware.client.features.api.Setting;
import slaughter.ware.client.ui.UIComponent;

public abstract class ExpandableComponent extends UIComponent {

    private final ExpandableBase expandableBase = new ExpandableBase();

    public void updateOpen() {
        expandableBase.update();
    }

    public void toggleOpen() {
        expandableBase.toggleOpen();
    }

    public boolean isOpen() {
        return expandableBase.isOpen();
    }

    public float getAnim() {
        return expandableBase.getAnimation();
    }

    public static abstract class ExpandableSettingComponent extends SettingComponent {
        private final ExpandableBase expandableBase = new ExpandableBase();

        protected ExpandableSettingComponent(Setting<?> setting) {
            super(setting);
        }

        protected void updateOpen() {
            expandableBase.update();
        }

        protected void toggleOpen() {
            expandableBase.toggleOpen();
        }

        protected float getOpenAnim() {
            return expandableBase.getAnimation();
        }

        protected boolean isOpen() {
            return expandableBase.isOpen();
        }

        protected void setOpen(boolean open) {
            expandableBase.setOpen(open);
        }
    }
}
