package me.viniciusroger.ihatehg.command.commands;

import me.viniciusroger.ihatehg.command.Command;
import me.viniciusroger.ihatehg.util.misc.ConfigUtil;

public class ConfigCommand extends Command {
    public ConfigCommand() {
        super("config", new String[]{"cfg"});
    }

    @Override
    public void onCommandExecute(String[] args) {
        if (args.length == 2) {
            switch (args[0]) {
                case "save":
                    ConfigUtil.createConfig(args[1]);

                    sendMessage("Saved config as: " + args[1]);

                    break;
                case "load":
                    boolean result = ConfigUtil.loadConfig(args[1]);

                    if (result) {
                        sendMessage("Loaded config!");
                    } else {
                        sendMessage("Failed to load config: not found or invalid");
                    }

                    break;
            }
        }
    }
}
