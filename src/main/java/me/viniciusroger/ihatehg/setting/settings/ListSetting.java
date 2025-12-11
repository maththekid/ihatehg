package me.viniciusroger.ihatehg.setting.settings;

import me.viniciusroger.ihatehg.setting.Setting;

public class ListSetting extends Setting<String> {
    private final String[] values;
    private int index;

    public ListSetting(String name, int index, String[] values) {
        super(name, values[index]);

        this.values = values;
        this.index = 0;
    }

    public String[] getValues() {
        return values;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public void setValue(String value) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                this.index = i;

                super.setValue(value);

                break;
            }
        }
    }
}
