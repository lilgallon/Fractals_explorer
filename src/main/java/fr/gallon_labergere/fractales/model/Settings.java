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

    /**
     * @return the current zoom level
     */
    public float getZoomLevel() {
        return zoomLevel;
    }

    /**
     * Update the current zoom level to a customized one
     * @param zoomLevel new zoom level value
     */
    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        //fire();
    }

    /**
     * @return the current fractal type
     */
    public SettingsController.FractalType getFractalType() {
        return fractalType;
    }

    public void setFractalType(SettingsController.FractalType fractalType) {
        this.fractalType = fractalType;
        fire();
    }

    /**
     * @return the current center (x)
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * Sets the x center
     * @param centerX
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
        fire();
    }

    /**
     * @return the current center (y)
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * Sets the current Y center
     * @param centerY
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
        //fire();
    }

    /**
     * TODO: precise description
     * @param x
     * @return
     */
    public float getViewX(int x) {
        return ((x - centerX) / zoomLevel);
    }

    /**
     * TODO: precise description
     * @param y
     * @return
     */
    public float getViewY(int y) {
        return ((y - centerY) / zoomLevel);
    }

    /**
     * TODO: precise description
     * @param x
     * @return
     */
    public float getMapX(float x) {
        return (x - centerX) * zoomLevel;
    }

    /**
     * TODO: precise description
     * @param y
     * @return
     */
    public float getMapY(float y) {
        return (y - centerY) * zoomLevel;
    }

    /**
     * @return the current progression
     */
    public int getProgression(){
        return this.progression;
    }

    /**
     * Updates the current progression
     * @param progression new progression
     */
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
