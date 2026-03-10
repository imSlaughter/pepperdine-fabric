package pepper.dine.client.command;

import pepper.dine.client.utils.chat.ChatUtil;

import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private final List<String> aliases;
    private final String usage;
    private final String description;

    protected Command(String usage, String description, String... aliases) {
        this.aliases = Arrays.stream(aliases).map(String::toLowerCase).toList();
        this.usage = usage;
        this.description = description;
    }

    public abstract void execute(CommandManager manager, String[] args);

    public List<String> suggest(CommandManager manager, String[] args) {
        return List.of();
    }

    public boolean matches(String name) {
        return aliases.contains(name.toLowerCase());
    }

    public String getPrimaryAlias() {
        return aliases.getFirst();
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    protected void chat(String message) {
        ChatUtil.addMess(message);
    }
}
