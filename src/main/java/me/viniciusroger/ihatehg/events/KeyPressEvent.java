package me.viniciusroger.ihatehg.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class KeyPressEvent extends Event {
    private final int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
