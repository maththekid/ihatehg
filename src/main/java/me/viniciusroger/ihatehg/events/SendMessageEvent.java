package me.viniciusroger.ihatehg.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class SendMessageEvent extends Event {
    private final String message;

    public SendMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
