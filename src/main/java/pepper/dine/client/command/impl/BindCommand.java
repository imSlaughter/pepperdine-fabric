package pepper.dine.client.command.impl;

import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import pepper.dine.PepperDine;
import pepper.dine.client.command.Command;
import pepper.dine.client.command.CommandManager;
import pepper.dine.client.modules.api.Module;

import java.util.List;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind <module> <key|none>", "Changes a module keybind.", "bind", "b");
    }

    @Override
    public void execute(CommandManager manager, String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("bind");
        }

        Module module = PepperDine.getInstance().getModuleRepository().getModuleByName(args[0]);
        if (module == null) {
            chat("Module not found: " + args[0]);
            return;
        }

        int key = parseKey(args[1]);
        module.setKey(key);
        chat(module.getName() + " bound to " + formatKey(key));
    }

    private int parseKey(String value) {
        if ("none".equalsIgnoreCase(value)) {
            return GLFW.GLFW_KEY_UNKNOWN;
        }

        String keyName = value.toUpperCase();
        if (!keyName.startsWith("KEY_")) {
            keyName = "KEY_" + keyName;
        }

        Integer translated = InputUtil.fromTranslationKey("key.keyboard." + keyName.substring(4).toLowerCase()).getCode();
        if (translated == GLFW.GLFW_KEY_UNKNOWN) {
            throw new IllegalArgumentException("bind");
        }
        return translated;
    }

    private String formatKey(int key) {
        if (key == GLFW.GLFW_KEY_UNKNOWN) {
            return "NONE";
        }
        return InputUtil.Type.KEYSYM.createFromCode(key).getLocalizedText().getString().toUpperCase();
    }

    @Override
    public List<String> suggest(CommandManager manager, String[] args) {
        if (args.length <= 1) {
            String partial = args.length == 0 ? "" : args[0].toLowerCase();
            return PepperDine.getInstance().getModuleRepository().getModules().stream()
                    .map(Module::getName)
                    .filter(name -> name.toLowerCase().startsWith(partial))
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .toList();
        }

        if (args.length == 2) {
            String partial = args[1].toLowerCase();
            return List.of("none", "r", "v", "x", "k").stream()
                    .filter(name -> name.startsWith(partial))
                    .toList();
        }

        return List.of();
    }
}
