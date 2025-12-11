package me.viniciusroger.ihatehg.util.misc;

public class TimerHelper {
    private long lastMs;

    public TimerHelper() {
        this.lastMs = System.currentTimeMillis();
    }

    public boolean hasTimeReached(long ms, boolean reset) {
        if (System.currentTimeMillis() - lastMs >= ms) {
            if (reset) {
                reset();
            }

            return true;
        }

        return false;
    }

    public void reset() {
        lastMs = System.currentTimeMillis();
    }
}
