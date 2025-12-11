package me.viniciusroger.ihatehg;

import me.viniciusroger.ihatehg.command.CommandManager;
import me.viniciusroger.ihatehg.module.ModuleManager;
import me.viniciusroger.ihatehg.util.misc.ConfigUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ihatehg")
public class IHateHG {
    private static final ModuleManager MODULE_MANAGER = new ModuleManager();
    private static final CommandManager COMMAND_MANAGER = new CommandManager();

    @Mod.EventHandler
    public void onFMLInit(FMLInitializationEvent event) {
        COMMAND_MANAGER.init();
        MODULE_MANAGER.init();

        ConfigUtil.createFolder();

        if (!ConfigUtil.loadConfig("default")) {
            ConfigUtil.createConfig("default");
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }

    public static CommandManager getCommandManager() {
        return COMMAND_MANAGER;
    }
}
