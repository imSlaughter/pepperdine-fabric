package pepper.dine.client.command.impl;

import pepper.dine.client.command.Command;
import pepper.dine.client.command.CommandManager;

public class PrefixCommand extends Command {

    public PrefixCommand() {
        super("prefix <char>", "Changes the chat command prefix.", "prefix");
    }

    @Override
    public void execute(CommandManager manager, String[] args) {
        if (args.length != 1 || args[0].isBlank()) {
            throw new IllegalArgumentException("prefix");
        }

        manager.setPrefix(args[0]);
        chat("Prefix set to " + args[0]);
    }
}
