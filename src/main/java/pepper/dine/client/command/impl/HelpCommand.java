package pepper.dine.client.command.impl;

import pepper.dine.client.command.Command;
import pepper.dine.client.command.CommandManager;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Shows available commands.", "help", "h");
    }

    @Override
    public void execute(CommandManager manager, String[] args) {
        chat("Commands:");
        for (Command command : manager.getCommands()) {
            chat(manager.getPrefix() + command.getUsage() + " - " + command.getDescription());
        }
    }
}
