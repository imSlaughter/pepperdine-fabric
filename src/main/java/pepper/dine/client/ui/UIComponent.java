package pepper.dine.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.input.KeyInput;

public abstract class UIComponent {

    protected final MinecraftClient mc = MinecraftClient.getInstance();
    private float x;
    private float y;
    private float width;
    private float height;
    private float alpha = 1.0f;

    public abstract void render(DrawContext context, int mouseX, int mouseY, float delta);

    public void keyPressed(KeyInput keyInput) {
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
    }

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    }

    protected float gap() {
        return 3.0f;
    }

    protected float offset() {
        return 5.0f;
    }

    protected boolean hovered(double mouseX, double mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
