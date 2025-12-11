package me.viniciusroger.ihatehg.module.modules;

import me.viniciusroger.ihatehg.module.Module;
import me.viniciusroger.ihatehg.setting.settings.BooleanSetting;
import me.viniciusroger.ihatehg.setting.settings.ListSetting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import org.lwjgl.input.Keyboard;

public class AutoRecraft extends Module {
    private final BooleanSetting lol = new BooleanSetting("idk", false);
    private final NumberSetting<Integer> a = new NumberSetting<>("fodase", 1, 1, 10, 1);
    private final ListSetting fodase = new ListSetting("sadbjfda", 0, new String[]{"penis", "pinto", "piroca", "caralho", "rola", "regiao pelvica", "genes peniano", "linguiça"});

    public AutoRecraft() {
        super("AutoRecraft", Keyboard.KEY_NONE);
    }
}
