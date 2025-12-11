package me.viniciusroger.ihatehg.module.modules;

import me.viniciusroger.ihatehg.events.PreUpdateEvent;
import me.viniciusroger.ihatehg.module.Module;
import me.viniciusroger.ihatehg.setting.settings.BooleanSetting;
import me.viniciusroger.ihatehg.setting.settings.KeybindSetting;
import me.viniciusroger.ihatehg.setting.settings.ListSetting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import me.viniciusroger.ihatehg.util.misc.TimerHelper;
import me.viniciusroger.ihatehg.util.player.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoRecraft extends Module {
    private final ListSetting mode = new ListSetting("Mode", 0, new String[]{"Automatic", "Manual", "Both"});
    private final KeybindSetting manualBind = new KeybindSetting("Manual bind", Keyboard.KEY_NONE);
    private final NumberSetting<Integer> startWith = new NumberSetting<>("Start with", 3, 0, 41, 1);
    private final NumberSetting<Integer> recraftDelay = new NumberSetting<>("Recraft Delay", 42, 1, 400, 1);
    private final BooleanSetting cocoaMode = new BooleanSetting("Cocoa", false);
    private final BooleanSetting mushroomMode = new BooleanSetting("Mushroom", true);
    private final ListSetting sortMode = new ListSetting("Sort by", 0, new String[]{"Size", "Index"});

    private final TimerHelper recraftTimer = new TimerHelper();

    private final HashMap<String, Integer> recraftMap = new HashMap<>();
    private boolean start = false;
    private int step = 1;

    @Override
    protected void onDisable() {
        reset();
    }

    public AutoRecraft() {
        super("AutoRecraft", Keyboard.KEY_NONE);
    }

    @SubscribeEvent
    public void onPreUpdate(PreUpdateEvent event) {
        if (mc.currentScreen == null || !(mc.currentScreen instanceof GuiInventory)) {
            reset();

            return;
        }

        if (start) {
            if (!recraftMap.isEmpty()) {
                if (recraftTimer.hasTimeReached(recraftDelay.getValue(), true)) {
                    if (recraftMap.size() == 2) { // outros recraft(por enquanto, só cocoa)
                        switch (step) {
                            case 1:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("bowl"), 1, 0, mc.thePlayer);

                                step++;

                                break;
                            case 2:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 1, 0, 0, mc.thePlayer);

                                step++;

                                break;
                            case 3:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("cocoa"), 1, 0, mc.thePlayer);

                                step++;

                                break;
                            case 4:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 0, mc.thePlayer);

                                step++;

                                break;
                            case 5:
                                if (mc.thePlayer.inventoryContainer.getSlot(0).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 0, 0, 1, mc.thePlayer);

                                break;
                            case 6:
                                if (mc.thePlayer.inventoryContainer.getSlot(3).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 3, 0, 1, mc.thePlayer);

                                break;
                            case 7:
                                if (mc.thePlayer.inventoryContainer.getSlot(2).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 1, mc.thePlayer);

                                break;
                            case 8:
                                reset();

                                break;
                        }
                    } else if (recraftMap.size() == 3) { // mushroom
                        switch (step) {
                            case 1:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("bowl"), 1, 0, mc.thePlayer);

                                step++;

                                break;
                            case 2:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 1, 0, 0, mc.thePlayer);

                                step++;

                                break;
                            case 3:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("red"), 1, 0, mc.thePlayer);

                                step++;

                                break;
                            case 4:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 0, mc.thePlayer);

                                step++;

                                break;
                            case 5:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("brown"), 1, 0, mc.thePlayer);

                                step++;

                                break;
                            case 6:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 3, 0, 0, mc.thePlayer);

                                step++;

                                break;
                            case 7:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 0, 0, 1, mc.thePlayer);

                                step++;

                                break;
                            case 8:
                                if (mc.thePlayer.inventoryContainer.getSlot(3).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 3, 0, 1, mc.thePlayer);

                                break;
                            case 9:
                                if (mc.thePlayer.inventoryContainer.getSlot(2).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 1, mc.thePlayer);

                                break;
                            case 10:
                                if (mc.thePlayer.inventoryContainer.getSlot(1).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 1, 0, 1, mc.thePlayer);

                                break;
                            case 11:
                                reset();

                                break;
                        }
                    }
                }
            } else {
                reset();
            }
        } else {
            if ((!mode.getValue().equals("Manual") && InventoryUtil.getTotalSoupsInInventory() <= startWith.getValue()) ||
                    (!mode.getValue().equals("Automatic") && Keyboard.isKeyDown(manualBind.getValue()))) {
                if (cocoaMode.getValue() && hasCocoaRecraft()) {
                    getRecraft(RecraftType.COCOA);
                } else if (mushroomMode.getValue() && hasMushroomRecraft()) {
                    getRecraft(RecraftType.MUSHROOM);
                }

                start = true;
            }
        }
    }

    private boolean hasCocoaRecraft() {
        boolean bowl = false;
        boolean cocoa = false;

        for (int i = 9; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemDye) {
                    if (EnumDyeColor.byMetadata(itemStack.getMetadata()) == EnumDyeColor.BROWN) {
                        cocoa = true;
                    }
                } else if (itemStack.getItem() == Items.bowl) {
                    bowl = true;
                }
            }
        }

        return bowl && cocoa;
    }

    private boolean hasMushroomRecraft() {
        AtomicBoolean bowl = new AtomicBoolean(false);
        AtomicBoolean red_mushroom = new AtomicBoolean(false);
        AtomicBoolean brown_mushroom = new AtomicBoolean(false);

        Arrays.stream(mc.thePlayer.inventory.mainInventory).forEach(itemStack -> {
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemBlock) {
                    Block block = ((ItemBlock) itemStack.getItem()).getBlock();

                    if (block == Blocks.red_mushroom) {
                        red_mushroom.set(true);
                    } else if (block == Blocks.brown_mushroom) {
                        brown_mushroom.set(true);
                    }
                } else {
                    if (itemStack.getItem() == Items.bowl) {
                        bowl.set(true);
                    }
                }
            }
        });

        return bowl.get() && red_mushroom.get() && brown_mushroom.get();
    }

    // o sort tá meio ruim
    // mas funciona, isso que importa!
    private void getRecraft(RecraftType type) {
        Map<Integer, HashMap<Integer, String>> itemSlotMap = new HashMap<>();

        for (int i = 9; i < 45; i++) {
            HashMap<Integer, String> itemMap = new HashMap<>();
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            switch (type) {
                case COCOA:
                    if (itemStack != null) {
                        if (itemStack.getItem() == Items.bowl) {
                            itemMap.put(itemStack.getMaxStackSize(), "bowl");
                            itemSlotMap.put(i, itemMap);
                        } else if (itemStack.getItem() instanceof ItemDye) {
                            if (EnumDyeColor.byMetadata(itemStack.getMetadata()) == EnumDyeColor.BROWN) {
                                itemMap.put(itemStack.getMaxStackSize(), "cocoa");
                            }
                        }
                    }

                    break;
                case MUSHROOM:
                    if (itemStack != null) {
                        if (itemStack.getItem() == Items.bowl) {
                            itemMap.put(itemStack.getMaxStackSize(), "bowl");
                            itemSlotMap.put(i, itemMap);
                        } else if (itemStack.getItem() instanceof ItemBlock) {
                            Block block = ((ItemBlock) itemStack.getItem()).getBlock();

                            if (block == Blocks.red_mushroom) {
                                itemMap.put(itemStack.getMaxStackSize(), "red");
                                itemSlotMap.put(i, itemMap);
                            } else if (block == Blocks.brown_mushroom) {
                                itemMap.put(itemStack.getMaxStackSize(), "brown");
                                itemSlotMap.put(i, itemMap);
                            }
                        }
                    }

                    break;
            }
        }

        itemSlotMap.entrySet().stream()
                .sorted((e1, e2) -> {
                    int size1;
                    int size2;

                    if (sortMode.getValue().equals("Size")) {
                        size1 = e1.getValue().entrySet().stream().findFirst().map(Map.Entry::getKey).orElse(0);
                        size2 = e2.getValue().entrySet().stream().findFirst().map(Map.Entry::getKey).orElse(0);

                        return size1 - size2;
                    } else {
                        size1 = e1.getKey();
                        size2 = e2.getKey();

                        return size2 - size1;
                    }
                })
                .forEach(result -> recraftMap.put(result.getValue().values().stream().findFirst().orElse("unknown"), result.getKey()));

        System.out.println(recraftMap.size());
    }

    private void reset() {
        recraftTimer.reset();
        recraftMap.clear();

        start = false;
        step = 1;
    }

    private enum RecraftType {
        COCOA,
        MUSHROOM
    }
}
