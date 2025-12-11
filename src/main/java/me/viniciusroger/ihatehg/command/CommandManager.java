package me.viniciusroger.ihatehg.command;

import me.viniciusroger.ihatehg.command.commands.ConfigCommand;
import me.viniciusroger.ihatehg.command.commands.PrefixCommand;
import me.viniciusroger.ihatehg.command.commands.SetValueCommand;
import me.viniciusroger.ihatehg.events.SendMessageEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {
    public String prefix = "-";

    private final ArrayList<Command> commands = new ArrayList<>();

    public void init() {
        commands.addAll(Arrays.asList(
                new ConfigCommand(),
                new PrefixCommand(),
                new SetValueCommand()
        ));

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onSendMessage(SendMessageEvent event) {
        String message = event.getMessage();

        if (message.startsWith(prefix)) {
            event.setCanceled(true);

            String[] split = message.substring(prefix.length()).split(" ");
            String name = split[0];
            String[] args = Arrays.copyOfRange(split, 1, split.length);

            commands.forEach(command -> {
                if (command.getName().equals(name)) {
                    command.onCommandExecute(args);
                } else if (command.getAlias() != null) {
                    for (String aliasName : command.getAlias()) {
                        if (name.equals(aliasName)) {
                            command.onCommandExecute(args);

                            return;
                        }
                    }
                }
            });
        }
    }
}
