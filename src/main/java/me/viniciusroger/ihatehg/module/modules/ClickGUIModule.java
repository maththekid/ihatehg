package me.viniciusroger.ihatehg.module.modules;

import me.viniciusroger.ihatehg.clickgui.ClickGUI;
import me.viniciusroger.ihatehg.events.PreUpdateEvent;
import me.viniciusroger.ihatehg.module.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ClickGUIModule extends Module {
    private ClickGUI clickGUI;

    public ClickGUIModule() {
        super("ClickGUI", Keyboard.KEY_INSERT);
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (clickGUI == null) {
            clickGUI = new ClickGUI();
        }

        mc.displayGuiScreen(clickGUI);

        setEnabled(false);
    }
}
