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

    /**
     * This method will be called whenever the Observable (model) spots a change.
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Settings))
            throw new IllegalStateException("Update cannot be triggered by anything else than Settings");
        Settings settings = (Settings) o;
        update(settings.getZoomLevel(), settings.getFractalType(), settings.getCenterX(), settings.getCenterY());
    }

    /**
     * This method will be defined in inherited classes.
     * @param zoomLevel
     * @param fractalType
     * @param x
     * @param y
     */
    abstract void update(float zoomLevel, SettingsController.FractalType fractalType, int x, int y);
}
