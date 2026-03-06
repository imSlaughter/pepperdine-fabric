package slaughter.ware.client.command.impl;

import slaughter.ware.SlaughterWare;
import slaughter.ware.client.command.Command;
import slaughter.ware.client.command.CommandManager;
import slaughter.ware.client.modules.api.Module;

import java.util.stream.Collectors;

public class ModulesCommand extends Command {

    public ModulesCommand() {
        super("modules", "Lists loaded modules.", "modules", "mods", "list");
    }

    @Override
    public void execute(CommandManager manager, String[] args) {
        String modules = SlaughterWare.getInstance().getModuleRepository().getModules().stream()
                .map(Module::getName)
                .collect(Collectors.joining(", "));
        chat(modules.isEmpty() ? "No modules loaded." : modules);
    }
}
