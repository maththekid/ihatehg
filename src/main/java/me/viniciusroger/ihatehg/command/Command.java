package me.viniciusroger.ihatehg.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Command {
    private final String name;
    private final String[] alias;

    protected final Minecraft mc = Minecraft.getMinecraft();

    public Command(String name, String[] alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String[] getAlias() {
        return alias;
    }

    public void onCommandExecute(String[] args) {}

    protected void sendMessage(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(String.format("[%sIHateHG%s] %s", EnumChatFormatting.RED, EnumChatFormatting.RESET, message)));
    }
}
