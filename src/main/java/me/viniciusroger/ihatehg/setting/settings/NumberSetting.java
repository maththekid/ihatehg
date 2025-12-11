package me.viniciusroger.ihatehg.setting.settings;

import me.viniciusroger.ihatehg.setting.Setting;
import me.viniciusroger.ihatehg.util.java.JavaUtil;

public class NumberSetting<T extends Number> extends Setting<T> {
    private final T min, max, increment;

    public NumberSetting(String name, T value, T min, T max, T increment) {
        super(name, value);

        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setValue(T value) {
        double result = Math.round(increment.doubleValue() * (value.doubleValue() / increment.doubleValue()));

        super.setValue((T) JavaUtil.getNumberFromType(result, min.getClass()));
    }
}
