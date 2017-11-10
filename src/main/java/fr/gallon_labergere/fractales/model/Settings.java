package fr.gallon_labergere.fractales.model;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.util.Observable;

public class Settings extends Observable {

    public static final float MIN_ZOOM = 0.5f;
    public static final float MAX_ZOOM = 50f;
    public static final float ZOOM_STEP = 0.5f;

    private float zoomLevel;
    private SettingsController.FractaleType fractaleType;

    public Settings(float zoomLevel, SettingsController.FractaleType fractaleType) {
        this.zoomLevel = zoomLevel;
        this.fractaleType = fractaleType;
    }

    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        setChanged();
        notifyObservers();
    }

    public SettingsController.FractaleType getFractaleType() {
        return fractaleType;
    }

    public void setFractaleType(SettingsController.FractaleType fractaleType) {
        this.fractaleType = fractaleType;
        setChanged();
        notifyObservers();
    }
}
