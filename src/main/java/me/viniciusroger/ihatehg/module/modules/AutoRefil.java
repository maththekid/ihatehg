package me.viniciusroger.ihatehg.module.modules;

import me.viniciusroger.ihatehg.IHateHG;
import me.viniciusroger.ihatehg.events.PreUpdateEvent;
import me.viniciusroger.ihatehg.module.Module;
import me.viniciusroger.ihatehg.setting.settings.BooleanSetting;
import me.viniciusroger.ihatehg.setting.settings.KeybindSetting;
import me.viniciusroger.ihatehg.setting.settings.ListSetting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import me.viniciusroger.ihatehg.util.misc.TimerHelper;
import me.viniciusroger.ihatehg.util.player.InventoryUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class AutoRefil extends Module {
    private final ListSetting mode = new ListSetting("Mode", 0, new String[]{"Automatic", "Manual", "Both"});
    private final KeybindSetting manualBind = new KeybindSetting("Manual bind", Keyboard.KEY_NONE);
    private final NumberSetting<Integer> startWith = new NumberSetting<>("Start with", 4, 0, 9, 1);
    private final NumberSetting<Integer> refilDelay = new NumberSetting<>("Refil delay", 42, 1, 400, 1);
    private final BooleanSetting randomize = new BooleanSetting("Randomize", false);

    private final TimerHelper refilTimer = new TimerHelper();

    private boolean start = false;

    public AutoRefil() {
        super("AutoRefil", Keyboard.KEY_NONE);
    }

    @Override
    protected void onDisable() {
        reset();
    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity == mc.thePlayer && event.world != mc.theWorld) {
            reset();
        }
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiInventory)) {
            reset();

            return;
        }

        AutoRecraft autoRecraft = IHateHG.getModuleManager().getModuleByClass(AutoRecraft.class);

        if (autoRecraft.isEnabled() && autoRecraft.start) {
            reset();

            return;
        }

        if (start) {
            if ((InventoryUtil.hasSoupInHotbar() && InventoryUtil.isHotbarFull())) {
                start = false;

                return;
            }

            if (refilTimer.hasTimeReached(refilDelay.getValue(), true)) {
                if (randomize.getValue()) {
                    ArrayList<Integer> soupSlots = InventoryUtil.getSoupSlots();

                    if (!soupSlots.isEmpty()) {
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, soupSlots.get(RandomUtils.nextInt(0, soupSlots.size())), 0, 1, mc.thePlayer);
                    } else {
                        start = false;
                    }
                } else {
                    int soupSlot = InventoryUtil.getSoupInInventory();

                    if (soupSlot != Integer.MIN_VALUE) {
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, soupSlot, 0, 1, mc.thePlayer);
                    } else {
                        start = false;
                    }
                }
            }
        } else {
            if ((!mode.getValue().equals("Automatic") && Keyboard.isKeyDown(manualBind.getValue())) ||
                    (!mode.getValue().equals("Manual") && InventoryUtil.getSoupAmountInHotbar() <= startWith.getValue())) {
                start = true;
            }
        }
    }

    private void reset() {
        refilTimer.reset();

        start = false;
    }
}
