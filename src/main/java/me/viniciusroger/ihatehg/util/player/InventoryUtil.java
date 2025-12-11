package me.viniciusroger.ihatehg.util.player;

import me.viniciusroger.ihatehg.util.AbstractUtil;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class InventoryUtil extends AbstractUtil {
    public static int getSoupInHotbar() {
        for (int i = 36; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemSoup) {
                return i - 36;
            }
        }

        return Integer.MIN_VALUE;
    }

    public static ArrayList<Integer> getSoupSlots() {
        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemSoup) {
                temp.add(i);
            }
        }

        return temp;
    }

    public static int getSoupInInventory() {
        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemSoup) {
                return i;
            }
        }

        return Integer.MIN_VALUE;
    }

    public static boolean hasSoupInHotbar() {
        return getSoupInHotbar() != Integer.MIN_VALUE;
    }

    public static int getSoupAmountInHotbar() {
        int counter = 0;

        for (int i = 36; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemSoup) {
                counter++;
            }
        }

        return counter;
    }

    public static boolean isHotbarFull() {
        int counter = 0;

        for (int i = 36; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null) {
                counter++;
            }
        }

        return counter == 9;
    }

    public static int getTotalSoupsInInventory() {
        int counter = 0;

        for (int i = 9; i < 45; i++) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();

            if (itemStack != null && itemStack.getItem() instanceof ItemSoup) {
                counter++;
            }
        }

        return counter;
    }
}
