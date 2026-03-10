package pepper.dine.client.ui.module;

public class ExpandableBase {

    private boolean open;
    private float animation;

    public void update() {
        float target = open ? 1.0f : 0.0f;
        animation += (target - animation) * 0.25f;
        if (Math.abs(target - animation) < 0.01f) {
            animation = target;
        }
    }

    public void toggleOpen() {
        open = !open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public float getAnimation() {
        return animation;
    }
}
