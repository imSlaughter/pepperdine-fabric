package slaughter.ware.client.command;

import slaughter.ware.client.command.impl.BindCommand;
import slaughter.ware.client.command.impl.HelpCommand;
import slaughter.ware.client.command.impl.ModulesCommand;
import slaughter.ware.client.command.impl.PrefixCommand;
import slaughter.ware.client.command.impl.ToggleCommand;
import slaughter.ware.client.utils.chat.ChatUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class CommandManager {

    private final List<Command> commands = new ArrayList<>();
    private String prefix = "^";

    public CommandManager() {
        register(new HelpCommand());
        register(new PrefixCommand());
        register(new ModulesCommand());
        register(new ToggleCommand());
        register(new BindCommand());
    }

    public boolean handleChatMessage(String message) {
        if (message == null || !message.startsWith(prefix)) {
            return false;
        }

        String body = message.substring(prefix.length()).trim();
        if (body.isEmpty()) {
            ChatUtil.addMess("Empty command. Use " + prefix + "help");
            return true;
        }

        String[] split = body.split("\\s+");
        String name = split[0];
        String[] args = new String[Math.max(0, split.length - 1)];
        if (split.length > 1) {
            System.arraycopy(split, 1, args, 0, split.length - 1);
        }

        Command command = getCommand(name);
        if (command == null) {
            ChatUtil.addMess("Unknown command: " + name);
            return true;
        }

        try {
            command.execute(this, args);
        } catch (Exception exception) {
            ChatUtil.addMess("Usage: " + prefix + command.getUsage());
        }
        return true;
    }

    public void register(Command command) {
        commands.add(command);
    }

    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.matches(name)) {
                return command;
            }
        }
        return null;
    }

    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getSuggestions(String message) {
        if (message == null || !message.startsWith(prefix)) {
            return List.of();
        }

        String body = message.substring(prefix.length());
        if (body.isBlank()) {
            return commands.stream()
                    .map(Command::getPrimaryAlias)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .map(alias -> prefix + alias)
                    .toList();
        }

        boolean endsWithSpace = body.endsWith(" ");
        String[] split = body.trim().split("\\s+");
        if (split.length == 0) {
            return List.of();
        }

        if (split.length == 1 && !endsWithSpace) {
            String partial = split[0].toLowerCase(Locale.ROOT);
            return commands.stream()
                    .map(Command::getPrimaryAlias)
                    .filter(alias -> alias.startsWith(partial))
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .map(alias -> prefix + alias)
                    .toList();
        }

        Command command = getCommand(split[0]);
        if (command == null) {
            return List.of();
        }

        String[] args = new String[Math.max(0, split.length - 1)];
        if (split.length > 1) {
            System.arraycopy(split, 1, args, 0, split.length - 1);
        }
        return command.suggest(this, args);
    }

    public String getHint(String message) {
        if (message == null || !message.startsWith(prefix)) {
            return null;
        }

        String body = message.substring(prefix.length()).trim();
        if (body.isEmpty()) {
            return prefix + "help";
        }

        String[] split = body.split("\\s+");
        Command command = getCommand(split[0]);
        if (command == null) {
            List<String> matches = getSuggestions(message);
            return matches.isEmpty() ? null : matches.getFirst();
        }

        return prefix + command.getUsage();
    }

    public String complete(String message) {
        List<String> suggestions = getSuggestions(message);
        if (suggestions.isEmpty()) {
            return message;
        }

        if (message != null && message.startsWith(prefix)) {
            String body = message.substring(prefix.length());
            String[] split = body.trim().isEmpty() ? new String[0] : body.trim().split("\\s+");
            boolean endsWithSpace = body.endsWith(" ");
            if (split.length >= 1 && (!split[0].isEmpty())) {
                Command command = getCommand(split[0]);
                if (command != null && (endsWithSpace || split.length > 1)) {
                    return replaceLastToken(message, suggestions.getFirst());
                }
            }
        }

        return suggestions.getFirst();
    }

    private String replaceLastToken(String message, String replacement) {
        int lastSpace = message.lastIndexOf(' ');
        if (lastSpace == -1) {
            return replacement;
        }
        return message.substring(0, lastSpace + 1) + replacement;
    }
}
