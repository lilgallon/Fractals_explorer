package fr.gallon_labergere.fractales.model;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.util.Observable;

public class Settings extends Observable {

    public static final float MIN_ZOOM = 0.5f;
    public static final float MAX_ZOOM = 50f;
    public static final float ZOOM_STEP = 0.5f;

    private float zoomLevel;
    private SettingsController.FractaleType fractaleType;
    private int centerX;
    private int centerY;

    public Settings(float zoomLevel, SettingsController.FractaleType fractaleType) {
        this.zoomLevel = zoomLevel;
        this.fractaleType = fractaleType;
        this.centerX = 0;
        this.centerY = 0;
    }

    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        fire();
    }

    public SettingsController.FractaleType getFractaleType() {
        return fractaleType;
    }

    public void setFractaleType(SettingsController.FractaleType fractaleType) {
        this.fractaleType = fractaleType;
        fire();
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
        fire();
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
        fire();
    }

    public int getViewX(int x) {
        return (int) ((x - centerX) / zoomLevel);
    }

    public int getViewY(int y) {
        return (int) ((y - centerY) / zoomLevel);
    }

    public int getMapX(int x) {
        return (int) ((x - centerX) * zoomLevel);
    }

    public int getMapY(int y) {
        return (int) ((y - centerY) * zoomLevel);
    }

    private void fire() {
        setChanged();
        notifyObservers();
    }
}
