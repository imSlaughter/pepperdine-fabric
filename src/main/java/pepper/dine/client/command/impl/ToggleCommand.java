package pepper.dine.client.command.impl;

import pepper.dine.PepperDine;
import pepper.dine.client.command.Command;
import pepper.dine.client.command.CommandManager;
import pepper.dine.client.modules.api.Module;

import java.util.List;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle <module>", "Toggles a module.", "toggle", "t");
    }

    @Override
    public void execute(CommandManager manager, String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("toggle");
        }

        Module module = PepperDine.getInstance().getModuleRepository().getModuleByName(args[0]);
        if (module == null) {
            chat("Module not found: " + args[0]);
            return;
        }

        module.toggle();
        chat(module.getName() + (module.isEnabled() ? " enabled" : " disabled"));
    }

    @Override
    public List<String> suggest(CommandManager manager, String[] args) {
        if (args.length > 1) {
            return List.of();
        }

        String partial = args.length == 0 ? "" : args[0].toLowerCase();
        return PepperDine.getInstance().getModuleRepository().getModules().stream()
                .map(Module::getName)
                .filter(name -> name.toLowerCase().startsWith(partial))
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }
}
