package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public abstract class SettingsObserver extends JPanel implements Observer {

    private SettingsController settingsController;
    private Settings settings;

    public SettingsObserver(SettingsController settingsController) {
        this.settingsController = settingsController;
        this.settings = settingsController.getSettingsModel();
        this.settings.addObserver(this);
    }

    public Settings getSettings() {
        return settings;
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Settings))
            throw new IllegalStateException("Update cannot be triggered by anything else than Settings");
        Settings settings = (Settings) o;
        update(settings.getZoomLevel(), settings.getFractaleType());
    }

    abstract void update(float zoomLevel, SettingsController.FractaleType fractaleType);
}
