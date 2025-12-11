package me.viniciusroger.ihatehg.module;

import me.viniciusroger.ihatehg.clickgui.ClickGUI;
import me.viniciusroger.ihatehg.events.KeyPressEvent;
import me.viniciusroger.ihatehg.module.modules.AutoRecraft;
import me.viniciusroger.ihatehg.module.modules.AutoRefil;
import me.viniciusroger.ihatehg.module.modules.AutoSoup;
import me.viniciusroger.ihatehg.module.modules.ClickGUIModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ModuleManager {
    private final ArrayList<Module> modules = new ArrayList<>();

    public void init() {
        modules.addAll(Arrays.asList(
                new ClickGUIModule(),
                new AutoRecraft(),
                new AutoSoup(),
                new AutoRefil()
        ));

        modules.forEach(Module::registerSettings);

        getModuleByClass(ClickGUIModule.class).clickGUI = new ClickGUI();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKeyPress(KeyPressEvent event) {
        for (Module module : modules) {
            if (event.getKey() == module.getKeybind()) {
                module.toggle();
            }
        }
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    @SuppressWarnings("unchecked")
    public <T extends Module> T getModuleByClass(Class<T> clazz) {
        return (T) modules.stream()
                .filter(module -> module.getClass().equals(clazz))
                .findFirst()
                .orElse(null);
    }

    public Module getModuleByName(String name) {
        return modules.stream()
                .filter(module -> module.getName().replace(" ", "").equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
