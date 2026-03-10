package pepper.dine.client.command.impl;

import pepper.dine.PepperDine;
import pepper.dine.client.command.Command;
import pepper.dine.client.command.CommandManager;
import pepper.dine.client.modules.api.Module;

import java.util.stream.Collectors;

public class ModulesCommand extends Command {

    public ModulesCommand() {
        super("modules", "Lists loaded modules.", "modules", "mods", "list");
    }

    @Override
    public void execute(CommandManager manager, String[] args) {
        String modules = PepperDine.getInstance().getModuleRepository().getModules().stream()
                .map(Module::getName)
                .collect(Collectors.joining(", "));
        chat(modules.isEmpty() ? "No modules loaded." : modules);
    }
}
