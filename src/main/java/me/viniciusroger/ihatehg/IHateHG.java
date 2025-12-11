package me.viniciusroger.ihatehg;

import me.viniciusroger.ihatehg.module.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ihatehg")
public class IHateHG {
    private static final ModuleManager MODULE_MANAGER = new ModuleManager();

    @Mod.EventHandler
    public void onFMLInit(FMLInitializationEvent event) {
        MODULE_MANAGER.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }
}
