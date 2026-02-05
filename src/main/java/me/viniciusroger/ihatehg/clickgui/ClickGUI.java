package me.viniciusroger.ihatehg.clickgui;

import me.viniciusroger.ihatehg.IHateHG;
import me.viniciusroger.ihatehg.setting.settings.BooleanSetting;
import me.viniciusroger.ihatehg.setting.settings.KeybindSetting;
import me.viniciusroger.ihatehg.setting.settings.ListSetting;
import me.viniciusroger.ihatehg.setting.settings.NumberSetting;
import net.minecraft.client.gui.GuiScreen;
import net.superblaubeere27.gui.ClientBaseRendererImpl;
import net.superblaubeere27.gui.IRenderer;
import net.superblaubeere27.gui.Utils;
import net.superblaubeere27.gui.Window;
import net.superblaubeere27.gui.components.*;
import net.superblaubeere27.gui.components.Label;
import net.superblaubeere27.gui.components.ScrollPane;
import net.superblaubeere27.gui.layout.GridLayout;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGUI extends GuiScreen {
    private final Window guiWindow = new Window("I Hate HG", 30, 30, 900, 500);
    private final IRenderer windowRenderer = new ClientBaseRendererImpl();
    private final ArrayList<ActionEventListener> renderListeners = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public ClickGUI() {
        Pane contentPane = new ScrollPane(windowRenderer, new GridLayout(1));

        ArrayList<Spoiler> modulesSpoiler = new ArrayList<>();

        IHateHG.getModuleManager().getModules().forEach(module -> {
            Pane modulePane = new Pane(windowRenderer, new GridLayout(1));

            Pane configPane = new Pane(windowRenderer, new GridLayout(2));

            configPane.addComponent(new Label(windowRenderer, "Enabled"));

            CheckBox enabledCheckbox = new CheckBox(windowRenderer, "");

            enabledCheckbox.setListener(toggled -> {
                module.setEnabled(toggled);

                return true;
            });

            renderListeners.add(() -> enabledCheckbox.setSelected(module.isEnabled()));

            configPane.addComponent(enabledCheckbox);

            configPane.addComponent(new Label(windowRenderer, "Module Keybind"));

            KeybindButton moduleKeybind = new KeybindButton(windowRenderer, Keyboard::getKeyName);

            moduleKeybind.setListener(key -> {
                switch (key) {
                    case Keyboard.KEY_ESCAPE:
                        return false;
                    case Keyboard.KEY_BACK:
                        module.setKeybind(Keyboard.KEY_NONE);

                        return true;
                }

                module.setKeybind(key);

                return true;
            });

            renderListeners.add(() -> moduleKeybind.setValue(module.getKeybind()));

            configPane.addComponent(moduleKeybind);

            Pane settingsPane = new Pane(windowRenderer, new GridLayout(2));

            if (!module.getSettings().isEmpty()) {
                module.getSettings().forEach(setting -> {
                    settingsPane.addComponent(new Label(windowRenderer, setting.getName()));

                    if (setting instanceof BooleanSetting) {
                        BooleanSetting booleanSetting = (BooleanSetting) setting;
                        CheckBox settingCheckBox = new CheckBox(windowRenderer, "");

                        settingCheckBox.setListener(checked -> {
                            booleanSetting.setValue(checked);

                            return true;
                        });

                        renderListeners.add(() -> settingCheckBox.setSelected(booleanSetting.getValue()));

                        settingsPane.addComponent(settingCheckBox);
                    } else if (setting instanceof NumberSetting) {
                        NumberSetting<Number> numberSetting = (NumberSetting<Number>) setting;
                        Slider.NumberType numberType;

                        if (numberSetting.getMin() instanceof Float || numberSetting.getMin() instanceof Double) {
                            numberType = Slider.NumberType.DECIMAL;
                        } else {
                            numberType = Slider.NumberType.INTEGER;
                        }

                        Slider settingSlider = new Slider(windowRenderer, numberSetting.getValue().doubleValue(), numberSetting.getMin().doubleValue(), numberSetting.getMax().doubleValue(), numberType);

                        settingSlider.setListener(value -> {
                            numberSetting.setValue(value);

                            return true;
                        });

                        renderListeners.add(() -> settingSlider.setValue(numberSetting.getValue().doubleValue()));

                        settingsPane.addComponent(settingSlider);
                    } else if (setting instanceof ListSetting) {
                        ListSetting listSetting = (ListSetting) setting;
                        ComboBox settingComboBox = new ComboBox(windowRenderer, listSetting.getValues(), listSetting.getIndex());

                        settingComboBox.setListener(index -> {
                            listSetting.setValue(listSetting.getValues()[index]);

                            return true;
                        });

                        renderListeners.add(() -> settingComboBox.setSelectedIndex(listSetting.getIndex()));

                        settingsPane.addComponent(settingComboBox);
                    } else if (setting instanceof KeybindSetting) {
                        KeybindSetting keybindSetting = (KeybindSetting) setting;
                        KeybindButton keybindButtonSetting = new KeybindButton(windowRenderer, Keyboard::getKeyName);

                        keybindButtonSetting.setListener(key -> {
                            switch (key) {
                                case Keyboard.KEY_ESCAPE:
                                    return false;
                                case Keyboard.KEY_BACK:
                                    keybindSetting.setValue(Keyboard.KEY_NONE);

                                    return true;
                            }

                            keybindSetting.setValue(key);

                            return true;
                        });

                        renderListeners.add(() -> keybindButtonSetting.setValue(keybindSetting.getValue()));

                        settingsPane.addComponent(keybindButtonSetting);
                    }
                });
            }

            modulePane.addComponent(configPane);

            if (!settingsPane.getComponents().isEmpty()) {
                modulePane.addComponent(settingsPane);
            }

            Spoiler moduleSpoiler = new Spoiler(windowRenderer, module.getName(), guiWindow.getWidth() - 15, modulePane);

            modulesSpoiler.add(moduleSpoiler);
        });

        modulesSpoiler.forEach(contentPane::addComponent);

        guiWindow.setContentPane(contentPane);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (ActionEventListener actionEventListener : renderListeners) {
            actionEventListener.onActionEvent();
        }

        Point point = Utils.calculateMouseLocation();

        guiWindow.mouseMoved(point.x * 2, point.y * 2);

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glLineWidth(1.0f);

        guiWindow.render(windowRenderer);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        guiWindow.mouseMoved(mouseX * 2, mouseY * 2);
        guiWindow.mousePressed(mouseButton, mouseX * 2, mouseY * 2);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        guiWindow.mouseMoved(mouseX * 2, mouseY * 2);
        guiWindow.mouseReleased(state, mouseX * 2, mouseY * 2);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        guiWindow.mouseMoved(mouseX * 2, mouseY * 2);

        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        guiWindow.mouseWheel(Mouse.getEventDWheel());
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        guiWindow.keyPressed(keyCode, typedChar);

        super.keyTyped(typedChar, keyCode);
    }
}