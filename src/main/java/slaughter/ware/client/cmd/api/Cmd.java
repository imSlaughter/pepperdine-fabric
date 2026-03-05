package slaughter.ware.client.cmd.api;

import lombok.Getter;
import slaughter.ware.client.utils.Minecraft.IMinecraft;

@Getter
public abstract class Cmd implements IMinecraft {

    private final String name, usage, description;



    public Cmd(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
    }
    public abstract void execute(String... args);
}
