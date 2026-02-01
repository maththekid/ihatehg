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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class AutoRecraft extends Module {
    private final ListSetting mode = new ListSetting("Mode", 0, new String[]{"Automatic", "Manual", "Both"});
    private final KeybindSetting manualBind = new KeybindSetting("Manual bind", Keyboard.KEY_NONE);
    private final NumberSetting<Integer> startWith = new NumberSetting<>("Start with", 3, 0, 41, 1);
    private final NumberSetting<Integer> recraftDelay = new NumberSetting<>("Recraft Delay", 42, 1, 400, 1);
    private final BooleanSetting cocoaMode = new BooleanSetting("Cocoa", false);
    private final BooleanSetting mushroomMode = new BooleanSetting("Mushroom", true);
    private final BooleanSetting cactusMode = new BooleanSetting("Cactus", true);
    private final ListSetting sortMode = new ListSetting("Sort by", 0, new String[]{"Size", "Index"});

    private final TimerHelper recraftTimer = new TimerHelper();

    private final HashMap<String, Integer> recraftMap = new HashMap<>();
    public boolean start = false;
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
                    if (recraftMap.size() == 2) { // outros recraft
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
                                if (cactusMode.getValue() && recraftMap.containsKey("cactus")) {
                                    mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("cactus"), 1, 0, mc.thePlayer);
                                } else if (cocoaMode.getValue() && recraftMap.containsKey("cocoa")) {
                                    mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, recraftMap.get("cocoa"), 1, 0, mc.thePlayer);
                                }

                                step++;

                                break;
                            case 4:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 0, mc.thePlayer);

                                step++;

                                break;
                            case 5:
                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 0, 0, 1, mc.thePlayer);

                                step++;

                                break;
                            case 6:
                                if (mc.thePlayer.inventoryContainer.getSlot(2).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 2, 0, 1, mc.thePlayer);

                                break;
                            case 7:
                                if (mc.thePlayer.inventoryContainer.getSlot(1).getStack() == null) {
                                    step++;

                                    break;
                                }

                                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, 1, 0, 1, mc.thePlayer);

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
                if (cactusMode.getValue() && hasCactusRecraft()) {
                    getRecraft(RecraftType.CACTUS);
                } else if (cocoaMode.getValue() && hasCocoaRecraft()) {
                    getRecraft(RecraftType.COCOA);
                } else if (mushroomMode.getValue() && hasMushroomRecraft()) {
                    getRecraft(RecraftType.MUSHROOM);
                }

                start = true;
            }
        }
    }

    private boolean hasCocoaRecraft() {
        AtomicBoolean bowl = new AtomicBoolean(false);
        AtomicBoolean cocoa = new AtomicBoolean(false);

        Arrays.stream(mc.thePlayer.inventory.mainInventory).forEach(itemStack -> {
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemDye) {
                    if (EnumDyeColor.byDyeDamage(itemStack.getMetadata()) == EnumDyeColor.BROWN) {
                        cocoa.set(true);
                    }
                } else if (itemStack.getItem() == Items.bowl) {
                    bowl.set(true);
                }
            }
        });

        return bowl.get() && cocoa.get();
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

    private boolean hasCactusRecraft() {
        AtomicBoolean bowl = new AtomicBoolean(false);
        AtomicBoolean cactus = new AtomicBoolean(false);

        Arrays.stream(mc.thePlayer.inventory.mainInventory).forEach(itemStack -> {
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemBlock) {
                    Block block = ((ItemBlock) itemStack.getItem()).getBlock();

                    if (block == Blocks.red_mushroom) {
                        cactus.set(true);
                    }
                } else {
                    if (itemStack.getItem() == Items.bowl) {
                        bowl.set(true);
                    }
                }
            }
        });

        return bowl.get() && cactus.get();
    }

    private void getRecraft(RecraftType type) {
        HashMap<Integer, String> itemSlotMap = new HashMap<>();

        for (int i = 9; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null) {
                switch (type) {
                    case COCOA:
                        if (itemStack.getItem() == Items.bowl) {
                            itemSlotMap.put(i, "bowl");
                        } else if (itemStack.getItem() instanceof ItemDye) {
                            if (EnumDyeColor.byDyeDamage(itemStack.getMetadata()) == EnumDyeColor.BROWN) {
                                itemSlotMap.put(i, "cocoa");
                            }
                        }

                        break;
                    case MUSHROOM:
                        if (itemStack.getItem() == Items.bowl) {
                            itemSlotMap.put(i, "bowl");
                        } else if (itemStack.getItem() instanceof ItemBlock) {
                            Block block = ((ItemBlock) itemStack.getItem()).getBlock();

                            if (block == Blocks.red_mushroom) {
                                itemSlotMap.put(i, "red");
                            } else if (block == Blocks.brown_mushroom) {
                                itemSlotMap.put(i, "brown");
                            }
                        }

                        break;
                    case CACTUS:
                        if (itemStack.getItem() == Items.bowl) {
                            itemSlotMap.put(i, "bowl");
                        } else if (itemStack.getItem() instanceof ItemBlock) {
                            Block block = ((ItemBlock) itemStack.getItem()).getBlock();

                            if (block == Blocks.cactus) {
                                itemSlotMap.put(i, "cactus");
                            }
                        }

                        break;
                }
            }
        }

        Stream<Map.Entry<Integer, String>> itemSlotStream = itemSlotMap.entrySet().stream();

        if (sortMode.getValue().equals("Size")) {
            itemSlotStream = itemSlotStream.sorted(Comparator.comparingInt(entrySet -> mc.thePlayer.inventoryContainer.getSlot(entrySet.getKey()).getStack().stackSize));
        } else {
            itemSlotStream = itemSlotStream.sorted(Map.Entry.comparingByKey((e1, e2) -> e2 - e1));
        }

        itemSlotStream.forEach(entrySet -> recraftMap.put(entrySet.getValue(), entrySet.getKey()));
    }

    private void reset() {
        recraftTimer.reset();
        recraftMap.clear();

        start = false;
        step = 1;
    }

    private enum RecraftType {
        COCOA,
        MUSHROOM,
        CACTUS
    }
}
