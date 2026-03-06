package slaughter.ware.client.ui;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import slaughter.ware.SlaughterWare;
import slaughter.ware.client.modules.ModuleCategory;
import slaughter.ware.client.modules.api.Module;

import java.util.ArrayList;
import java.util.List;

public final class ClickGuiScreen extends Screen {

    private static final int PANEL_WIDTH = 120;
    private static final int PANEL_GAP = 5;
    private static final int HEADER_HEIGHT = 18;
    private static final int MODULE_HEIGHT = 12;
    private static final int MODULE_GAP = 2;

    private Module bindingModule;

    public ClickGuiScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0x78000000);

        int startX = getStartX();
        int startY = getStartY();

        int panelX = startX;
        for (ModuleCategory category : ModuleCategory.values()) {
            List<Module> modules = getModules(category);
            int panelHeight = HEADER_HEIGHT + 4 + modules.size() * (MODULE_HEIGHT + MODULE_GAP) + 2;

            context.fill(panelX, startY, panelX + PANEL_WIDTH, startY + panelHeight, 0xFF1F1F1F);
            context.fill(panelX, startY, panelX + PANEL_WIDTH, startY + HEADER_HEIGHT, 0xFF2B2B2B);

            String categoryName = prettyName(category.name());
            int categoryWidth = textRenderer.getWidth(categoryName);
            context.drawText(textRenderer, categoryName, panelX + (PANEL_WIDTH - categoryWidth) / 2, startY + 5, 0xFFFFFFFF, false);

            int moduleY = startY + HEADER_HEIGHT + 2;
            for (Module module : modules) {
                boolean hovered = isHovered(panelX + 2, moduleY, PANEL_WIDTH - 4, MODULE_HEIGHT, mouseX, mouseY);
                boolean binding = module == bindingModule;
                int moduleColor = module.isEnabled() ? 0xFF373737 : 0xFF242424;
                if (hovered) {
                    moduleColor = module.isEnabled() ? 0xFF404040 : 0xFF2C2C2C;
                }

                context.fill(panelX + 2, moduleY, panelX + PANEL_WIDTH - 2, moduleY + MODULE_HEIGHT, moduleColor);
                context.drawText(textRenderer, module.getName(), panelX + 5, moduleY + 2, 0xFFFFFFFF, false);

                if (binding) {
                    String bindText = "[" + keyName(module.getKey()) + "]";
                    int bindWidth = textRenderer.getWidth(bindText);
                    context.drawText(textRenderer, bindText, panelX + PANEL_WIDTH - bindWidth - 5, moduleY + 2, 0xFFFFFFFF, false);
                }

                moduleY += MODULE_HEIGHT + MODULE_GAP;
            }

            panelX += PANEL_WIDTH + PANEL_GAP;
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        int startX = getStartX();
        int startY = getStartY();
        int panelX = startX;
        for (ModuleCategory category : ModuleCategory.values()) {
            List<Module> modules = getModules(category);
            int moduleY = startY + HEADER_HEIGHT + 2;
            for (Module module : modules) {
                if (isHovered(panelX + 2, moduleY, PANEL_WIDTH - 4, MODULE_HEIGHT, click.x(), click.y())) {
                    if (click.button() == 0) {
                        module.toggle();
                    } else if (click.button() == 2) {
                        bindingModule = bindingModule == module ? null : module;
                    }
                    return true;
                }
                moduleY += MODULE_HEIGHT + MODULE_GAP;
            }
            panelX += PANEL_WIDTH + PANEL_GAP;
        }

        return super.mouseClicked(click, doubled);
    }

    @Override
    public boolean keyPressed(KeyInput keyInput) {
        if (bindingModule != null) {
            if (keyInput.getKeycode() == GLFW.GLFW_KEY_ESCAPE) {
                bindingModule = null;
                return true;
            }

            bindingModule.setKey(keyInput.getKeycode());
            bindingModule = null;
            return true;
        }

        if (keyInput.getKeycode() == GLFW.GLFW_KEY_ESCAPE || keyInput.getKeycode() == GLFW.GLFW_KEY_P) {
            close();
            return true;
        }

        return super.keyPressed(keyInput);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private List<Module> getModules(ModuleCategory category) {
        List<Module> modules = new ArrayList<>();
        for (Module module : SlaughterWare.getInstance().getModuleRepository().getModules()) {
            if (module.getCategory() == category) {
                modules.add(module);
            }
        }
        return modules;
    }

    private boolean isHovered(double x, double y, double width, double height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    private String prettyName(String value) {
        return value.charAt(0) + value.substring(1).toLowerCase();
    }

    private int getStartX() {
        int totalWidth = ModuleCategory.values().length * PANEL_WIDTH + (ModuleCategory.values().length - 1) * PANEL_GAP;
        return Math.max(8, (width - totalWidth) / 2);
    }

    private int getStartY() {
        int maxModules = 0;
        for (ModuleCategory category : ModuleCategory.values()) {
            maxModules = Math.max(maxModules, getModules(category).size());
        }

        int maxHeight = HEADER_HEIGHT + 4 + maxModules * (MODULE_HEIGHT + MODULE_GAP) + 2;
        return Math.max(12, (height - maxHeight) / 2 - 28);
    }

    private String keyName(int key) {
        if (key == GLFW.GLFW_KEY_UNKNOWN) {
            return "NONE";
        }

        String glfwName = GLFW.glfwGetKeyName(key, 0);
        if (glfwName != null) {
            return glfwName.toUpperCase();
        }

        return switch (key) {
            case GLFW.GLFW_KEY_P -> "P";
            case GLFW.GLFW_KEY_LEFT_SHIFT -> "LSHIFT";
            case GLFW.GLFW_KEY_LEFT_CONTROL -> "LCTRL";
            case GLFW.GLFW_KEY_RIGHT_CONTROL -> "RCTRL";
            case GLFW.GLFW_KEY_LEFT_ALT -> "LALT";
            case GLFW.GLFW_KEY_RIGHT_ALT -> "RALT";
            case GLFW.GLFW_KEY_SPACE -> "SPACE";
            default -> "KEY" + key;
        };
    }
}
