package fr.gallon_labergere.fractales.controller;

import fr.gallon_labergere.fractales.model.Settings;

public class SettingsController {

    private Settings settingsModel;

    public SettingsController(Settings settingsModel) {
        this.settingsModel = settingsModel;
    }

    public Settings getSettingsModel() {
        return settingsModel;
    }

    public void zoomIn() {
        if (settingsModel.getZoomLevel() >= Settings.MAX_ZOOM)
            return;
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() + Settings.ZOOM_STEP);
    }

    public void zoomOut() {
        if (settingsModel.getZoomLevel() <= Settings.MIN_ZOOM)
            return;
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() - Settings.ZOOM_STEP);
    }

    public enum FractaleType {
        MANDELBROT,
        OTHER
    }
}
