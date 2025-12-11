package me.viniciusroger.ihatehg.command.commands;

import me.viniciusroger.ihatehg.IHateHG;
import me.viniciusroger.ihatehg.command.Command;
import me.viniciusroger.ihatehg.module.Module;
import me.viniciusroger.ihatehg.setting.Setting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import me.viniciusroger.ihatehg.util.java.JavaUtil;

public class SetValueCommand extends Command {
    public SetValueCommand() {
        super("setvalue", null);
    }

    @Override
    public void onCommandExecute(String[] args) {
        if (args.length >= 3) {
            Module module = IHateHG.getModuleManager().getModuleByName(args[0]);

            if (module != null) {
                Setting<?> setting = module.getSettingByName(args[1]);

                if (setting != null) {
                    if (setting instanceof NumberSetting) {
                        try {
                            @SuppressWarnings("unchecked")
                            NumberSetting<Number> numberSetting = (NumberSetting<Number>) setting;
                            Double value = Double.parseDouble(args[2]);

                            numberSetting.setValue(value);

                            sendMessage("Value changed to: " + numberSetting.getValue());
                        } catch (Exception ignored) {
                            sendMessage("Invalid value");
                        }
                    } else {
                        sendMessage("Only number setting is valid");
                    }
                } else {
                    sendMessage("Invalid setting name");
                }
            } else {
                sendMessage("Invalid module name");
            }
        }
    }
}
