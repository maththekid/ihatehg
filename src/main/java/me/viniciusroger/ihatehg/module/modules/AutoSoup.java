package me.viniciusroger.ihatehg.module.modules;

import me.viniciusroger.ihatehg.events.KeyPressEvent;
import me.viniciusroger.ihatehg.events.PreUpdateEvent;
import me.viniciusroger.ihatehg.module.Module;
import me.viniciusroger.ihatehg.setting.settings.BooleanSetting;
import me.viniciusroger.ihatehg.setting.settings.KeybindSetting;
import me.viniciusroger.ihatehg.setting.settings.ListSetting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import me.viniciusroger.ihatehg.util.misc.TimerHelper;
import me.viniciusroger.ihatehg.util.player.InventoryUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AutoSoup extends Module {
    private final ListSetting mode = new ListSetting("Mode", 0, new String[]{"Automatic", "Manual"});
    private final KeybindSetting manualBind = new KeybindSetting("Manual bind", Keyboard.KEY_NONE);
    private final NumberSetting<Double> health = new NumberSetting<>("Health", 10.0, 0.5, 20.0, 0.5);
    private final BooleanSetting dropSoup = new BooleanSetting("Drop Soup", true);
    private final NumberSetting<Integer> healDelay = new NumberSetting<>("Heal delay", 50, 1, 400, 1);
    private final NumberSetting<Integer> dropDelay = new NumberSetting<>("Drop delay", 50, 1, 400, 1);
    private final NumberSetting<Integer> switchDelay = new NumberSetting<>("Switch Delay", 50, 1, 400, 1);

    private final TimerHelper healTimer = new TimerHelper();
    private final TimerHelper dropTimer = new TimerHelper();
    private final TimerHelper switchTimer = new TimerHelper();

    private int soupIndex = Integer.MIN_VALUE;
    private int originalIndex = Integer.MIN_VALUE;
    private int step = 1;
    private boolean start = false;

    public AutoSoup() {
        super("AutoSoup", Keyboard.KEY_NONE);
    }

    @Override
    protected void onDisable() {
        reset();
        originalIndex = Integer.MIN_VALUE;
    }

    @SubscribeEvent
    public void onKeyPress(KeyPressEvent event) {
        if (mode.getValue().equals("Manual") && event.getKey() == manualBind.getValue()) {
            originalIndex = mc.thePlayer.inventory.currentItem;

            start = true;
        }
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (mc.currentScreen != null) {
            reset();

            return;
        }

        if (start) {
            if (step >= 2 && step <= 3 && mc.thePlayer.inventory.currentItem != soupIndex) {
                mc.thePlayer.inventory.currentItem = soupIndex;
            }

            switch (step) {
                case 1:
                    if (switchTimer.hasTimeReached(switchDelay.getValue(), true)) {
                        mc.thePlayer.inventory.currentItem = soupIndex;

                        step++;
                    }

                    break;
                case 2:
                    if (healTimer.hasTimeReached(healDelay.getValue(), true)) {
                        if (mc.gameSettings.keyBindUseItem.isKeyDown()) {
                            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);

                            step++;

                            break;
                        }

                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    }

                    break;
                case 3:
                    if (dropSoup.getValue()) {
                        if (mc.thePlayer.inventoryContainer.getSlot(soupIndex + 36).getStack() == null) {
                            step++;

                            break;
                        }

                        if (dropTimer.hasTimeReached(dropDelay.getValue(), true)) {
                            C07PacketPlayerDigging.Action action = GuiScreen.isCtrlKeyDown() ? C07PacketPlayerDigging.Action.DROP_ALL_ITEMS : C07PacketPlayerDigging.Action.DROP_ITEM;
                            mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(action, BlockPos.ORIGIN, EnumFacing.DOWN));
                        }
                    } else {
                        step++;
                    }

                    break;
                case 4:
                    if (switchTimer.hasTimeReached(switchDelay.getValue(), true)) {
                        mc.thePlayer.inventory.currentItem = originalIndex;

                        step++;
                    }

                    break;
                case 5:
                    reset();

                    break;
            }
        } else {
            soupIndex = InventoryUtil.getSoupInHotbar();

            if (soupIndex != Integer.MIN_VALUE) {
                if (mode.getValue().equals("Automatic") && mc.thePlayer.getHealth() <= health.getValue()) {
                    originalIndex = mc.thePlayer.inventory.currentItem;

                    start = true;
                }
            }
        }
    }

    private void reset() {
        healTimer.reset();
        dropTimer.reset();
        switchTimer.reset();

        originalIndex = Integer.MIN_VALUE;
        soupIndex = Integer.MIN_VALUE;
        start = false;
        step = 1;
    }
}
