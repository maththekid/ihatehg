package me.viniciusroger.ihatehg.module;

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

        MinecraftForge.EVENT_BUS.register(this);
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    @SubscribeEvent
    public void onKeyPress(KeyPressEvent event) {
        for (Module module : modules) {
            if (event.getKey() == module.getKeybind()) {
                module.toggle();
            }
        }
    }
}
