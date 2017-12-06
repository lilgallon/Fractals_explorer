package fr.gallon_labergere.fractales.model;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.util.Observable;

public class Settings extends Observable {

   // public static final float MIN_ZOOM = 0.5f;
   // public static final float MAX_ZOOM = 100000f;
   // public static final float ZOOM_MULTIPLICATOR = 1.2f;
   // public static final int   MIN_ITERATIONS = 25;
   // public static final int   MAX_ITERATIONS = 500;

    private float zoomLevel;
    private SettingsController.FractalType fractalType;
    private int centerX;
    private int centerY;
    private int progression;
    private int iterations;
    private SettingsController.ColorationMode colorationMode;

    public Settings(float zoomLevel, SettingsController.FractalType fractalType) {
        if(fractalType==null){throw new NullPointerException("The fractalType must be defined!");}
        this.zoomLevel = zoomLevel;
        this.fractalType = fractalType;
        this.centerX = 0;
        this.centerY = 0;
        this.progression = 0;
        this.iterations = fractalType.getDrawer().getInitialIterations();
        this.colorationMode = SettingsController.ColorationMode.ORIGINAL;
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
        //fire();
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
        fire();
    }

    /**
     * Convert x to the map (fractale) coordinates
     * @param x
     * @return
     */
    public float getViewX(float x) {
        return ((x - centerX) / zoomLevel);
    }

    /**
     * Convert y to the map (fractale) coordinates
     * @param y
     * @return
     */
    public float getViewY(float y) {
        return ((y - centerY) / zoomLevel);
    }

    /**
     * Convert x to the view coordinates
     * @param x
     * @return
     */
    public float getMapX(float x) {
        return (x - centerX) * zoomLevel;
    }

    /**
     * Convert y to the view coordinates
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

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
        fire();
    }

    public SettingsController.ColorationMode getColorationMode() {
        return colorationMode;
    }

    public void setColorationMode(SettingsController.ColorationMode colorationMode) {
        this.colorationMode = colorationMode;
        fire();
    }
}
