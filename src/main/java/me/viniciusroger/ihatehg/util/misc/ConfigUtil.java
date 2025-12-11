package me.viniciusroger.ihatehg.util.misc;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.viniciusroger.ihatehg.IHateHG;
import me.viniciusroger.ihatehg.module.Module;
import me.viniciusroger.ihatehg.setting.Setting;
import me.viniciusroger.ihatehg.setting.settings.BooleanSetting;
import me.viniciusroger.ihatehg.setting.settings.KeybindSetting;
import me.viniciusroger.ihatehg.setting.settings.ListSetting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import me.viniciusroger.ihatehg.util.AbstractUtil;
import me.viniciusroger.ihatehg.util.java.JavaUtil;

import java.io.*;

public class ConfigUtil extends AbstractUtil {
    private static final File CONFIG_DIR = new File(mc.mcDataDir, "ratfolder");

    public static void createFolder() {
        @SuppressWarnings("unused")
        boolean result = CONFIG_DIR.mkdir();
    }

    public static void createConfig(String name) {
        File configFile = new File(CONFIG_DIR, name + ".json");

        if (!configFile.exists()) {
            try {
                @SuppressWarnings("unused")
                boolean result = configFile.createNewFile();
            } catch (Exception ignored) {}
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(configFile))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonObject configObject = new JsonObject();
            JsonObject modulesObject = new JsonObject();

            for (Module module : IHateHG.getModuleManager().getModules()) {
                JsonObject moduleObject = new JsonObject();
                JsonObject settings = new JsonObject();

                moduleObject.addProperty("enabled", module.isEnabled());
                moduleObject.addProperty("keybind", module.getKeybind());

                for (Setting<?> setting : module.getSettings()) {
                    if (setting instanceof BooleanSetting) {
                        settings.addProperty(setting.getName(), (Boolean) setting.getValue());
                    } else if (setting instanceof ListSetting) {
                        settings.addProperty(setting.getName(), (String) setting.getValue());
                    } else if (setting instanceof NumberSetting) {
                        @SuppressWarnings("unchecked")
                        NumberSetting<Number> numberSetting = (NumberSetting<Number>) setting;

                        settings.addProperty(setting.getName(), JavaUtil.getNumberFromType(numberSetting.getValue(), numberSetting.getMin().getClass()));
                    } else if (setting instanceof KeybindSetting) {
                        KeybindSetting keybindSetting = (KeybindSetting) setting;

                        settings.addProperty(setting.getName(), keybindSetting.getValue());
                    }
                }

                moduleObject.add("settings", settings);
                modulesObject.add(module.getName(), moduleObject);
            }

            configObject.add("modules", modulesObject);

            JsonWriter jsonWriter = new JsonWriter(bufferedWriter);

            jsonWriter.setIndent("\t");

            gson.toJson(configObject, jsonWriter);
        } catch (Exception ignored) {}
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static boolean loadConfig(String name) {
        File configFile = new File(CONFIG_DIR, name + ".json");

        if (!configFile.exists()) {
            return false;
        } else {
            try {
                JsonObject modulesObject = new JsonParser().parse(new JsonReader(new FileReader(configFile))).getAsJsonObject();

                if (modulesObject == null) {
                    return false;
                }

                for (Module module : IHateHG.getModuleManager().getModules()) {
                    JsonObject moduleObject = modulesObject.getAsJsonObject("modules").getAsJsonObject(module.getName());

                    if (moduleObject == null) {
                        continue;
                    }

                    module.setEnabled(moduleObject.get("enabled").getAsBoolean());
                    module.setKeybind(moduleObject.get("keybind").getAsInt());

                    for (Setting setting : module.getSettings()) {
                        JsonObject settingObject = moduleObject.getAsJsonObject("settings");

                        if (setting instanceof BooleanSetting) {
                            setting.setValue(settingObject.get(setting.getName()).getAsBoolean());
                        } else if (setting instanceof ListSetting) {
                            setting.setValue(settingObject.get(setting.getName()).getAsString());
                        } else if (setting instanceof NumberSetting) {
                            NumberSetting<Number> numberSetting = (NumberSetting<Number>) setting;

                            numberSetting.setValue(JavaUtil.getNumberFromType(settingObject.get(setting.getName()).getAsNumber(), numberSetting.getMin().getClass()));
                        } else if (setting instanceof KeybindSetting) {
                            KeybindSetting keybindSetting = (KeybindSetting) setting;

                            keybindSetting.setValue(settingObject.get(setting.getName()).getAsInt());
                        }
                    }

                    modulesObject.add(module.getName(), moduleObject);
                }

            } catch (Exception ignored) {
            }

            return true;
        }
    }
}
