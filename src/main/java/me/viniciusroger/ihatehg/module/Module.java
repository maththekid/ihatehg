package me.viniciusroger.ihatehg.module;

import me.viniciusroger.ihatehg.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Module {
    private final String name;
    private int keybind;

    private final ArrayList<Setting<?>> settings = new ArrayList<>();
    private boolean enabled = false;

    protected final Minecraft mc = Minecraft.getMinecraft();

    public Module(String name, int keybind) {
        this.name = name;
        this.keybind = keybind;
    }

    public String getName() {
        return name;
    }

    public int getKeybind() {
        return keybind;
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            enable();
        } else {
            disable();
        }
    }

    public void registerSettings() {
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (Setting.class.isAssignableFrom(field.getType())) {
                try {
                    Object settingObject = field.get(this);

                    if (settingObject != null) {
                        settings.add((Setting<?>) settingObject);
                    }
                } catch (IllegalAccessException ignored) {}
            }
        }
    }

    public Setting<?> getSettingByName(String name) {
        return settings.stream()
                .filter(setting -> setting.getName().replace(" ", "").equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    protected void onEnable() {}

    protected void onDisable() {}

    private void enable() {
        onEnable();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);

        onDisable();
    }

    public void toggle() {
        enabled = !enabled;

        if (enabled) {
            enable();
        } else {
            disable();
        }
    }
}
