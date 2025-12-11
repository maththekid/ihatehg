package me.viniciusroger.ihatehg.command.commands;

import me.viniciusroger.ihatehg.IHateHG;
import me.viniciusroger.ihatehg.command.Command;

public class PrefixCommand extends Command {
    public PrefixCommand() {
        super("prefix", new String[]{"p"});
    }

    @Override
    public void onCommandExecute(String[] args) {
        if (args.length >= 1) {
            IHateHG.getCommandManager().prefix = args[0];

            sendMessage("Prefix changed to: " + args[0]);
        }
    }
}
