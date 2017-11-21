package fr.gallon_labergere.fractales.model;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.util.Observable;

public class Settings extends Observable {

    public static final float MIN_ZOOM = 0.5f;
    public static final float MAX_ZOOM = 50f;
    public static final float ZOOM_STEP = 0.5f;

    private float zoomLevel;
    private SettingsController.FractalType fractalType;
    private int centerX;
    private int centerY;
    private int progression;

    public Settings(float zoomLevel, SettingsController.FractalType fractalType) {
        this.zoomLevel = zoomLevel;
        this.fractalType = fractalType;
        this.centerX = 0;
        this.centerY = 0;
        this.progression = 0;
    }

    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        fire();
    }

    public SettingsController.FractalType getFractalType() {
        return fractalType;
    }

    public void setFractalType(SettingsController.FractalType fractalType) {
        this.fractalType = fractalType;
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

    public double getMapX(double x) {
        return (x - centerX) * zoomLevel;
    }

    public double getMapY(double y) {
        return (y - centerY) * zoomLevel;
    }

    public int getProgression(){
        return this.progression;
    }

    public void setProgression(int progression){
        this.progression = progression;
    }



    /**
     * When the model (this class) spots a change, we need to notify the observers about it.
     * This method is used to say to the observers that the observable class has been changed.
     */
    private void fire() {
        setChanged();
        notifyObservers();
    }

}
