package fr.gallon_labergere.fractales.model;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.awt.image.BufferedImage;
import java.util.Observable;

/* This file is part of the JavaFractal project.
 *
 * JavaFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaFractal.  If not, see <http://www.gnu.org/licenses/>.
 * Authors : Lilian Gallon, Rémi Labergère
 */

/**
 * It is the model.
 */
public class Settings extends Observable {

    private SettingsController.FractalType fractalType;
    private SettingsController.ColorationMode colorationMode;
    private BufferedImage image;

    public static final int MIN_THREAD_COUNT = 1;
    public static final int MAX_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private float zoomLevel;
    private long centerX;
    private long centerY;
    private int iterations;
    private int threadCount;

    /**
     * Constructor
     * @param zoomLevel the initial zoom level
     * @param fractalType the initial selected fractal
     */
    public Settings(float zoomLevel, SettingsController.FractalType fractalType) {
        // We decided that the fractal type must be defined
        if (fractalType == null) throw new NullPointerException("The fractalType must be defined!");
        this.zoomLevel = zoomLevel;
        this.fractalType = fractalType;
        this.centerX = 0;
        this.centerY = 0;
        this.iterations = fractalType.getDrawer().getInitialIterations();
        this.colorationMode = SettingsController.ColorationMode.ORIGINAL;
        this.threadCount = MIN_THREAD_COUNT;
    }

    /**
     * Getter on BufferedImage image
     * @return the current image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Update the displayed image
     * MVC: does not fire an event
     * @param image new image
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Getter on the zoomLevel
     * @return the current zoom level
     */
    public float getZoomLevel() {
        return zoomLevel;
    }

    /**
     * Update the current zoom level to a customized one
     * It does not fire a change event since every time we zoom, we adjust
     * the center, so the change event will be fired at the center change event.
     * MVC: does not fire any event
     * @param zoomLevel new zoom level value
     */
    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    /**
     * @return the current fractal type
     */
    public SettingsController.FractalType getFractalType() {
        return fractalType;
    }

    /**
     * Change the current fractal
     * MVC: fires an event
     * @param fractalType new fractal type
     */
    public void setFractalType(SettingsController.FractalType fractalType) {
        this.fractalType = fractalType;
        fire();
    }

    /*
    * Important note: if the centerY or centerX reach the MAX_INT, it will change the position in the fractale.
    * That's why there is a maximum zoom to prevent this. We couldn't use long instead of int because image.setRGB
    * needs int as coordinates and g.drawRect too.
    */

    /**
     * @return the current center (x)
     */
    public long getCenterX() {
        return centerX;
    }

    /**
     * Sets the x center
     * MVC: does not fire an event
     * @param centerX new x pos of the center
     */
    public void setCenterX(long centerX) {
        this.centerX = centerX;
    }

    /**
     * @return the current center (y)
     */
    public long getCenterY() {
        return centerY;
    }

    /**
     * Sets the current Y center
     * MVC: fires an event
     * @param centerY new y pos of the center
     */
    public void setCenterY(long centerY) {
        this.centerY = centerY;
        fire();
    }

    /**
     * Convert x to the view coordinates
     * @param x pos to convert
     * @return x converted to the view
     */
    public int getViewX(float x) {
        return (int) ((x * zoomLevel) + centerX);
    }

    /**
     * Convert y to the view coordinates
     * @param y  pos to convert
     * @return y converted to the view
     */
    public int getViewY(float y) {
        return (int) ((y * zoomLevel) + centerY);
    }

    /**
     * Convert x to the map coordinates
     * @param x pos to convert
     * @return converted to the map
     */
    public float getMapX(int x) {
        return (x - centerX) / zoomLevel;
    }

    /**
     * Convert y to the map coordinates
     * @param y pos to convert
     * @return y converted to the map
     */
    public float getMapY(int y) {
        return (y - centerY) / zoomLevel;
    }

    /**
     * When the model (this class) spots a change, we need to notify the observers about it.
     * This method is used to say to the observers that the observable class has been changed.
     */
    public void fire() {
        setChanged();
        notifyObservers();
    }

    /**
     * Getter on the iterations
     * @return the current number of iterations
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Change the number of iterations (it means the precision)
     * MVC: fires an event
     * @param iterations new number of iterations
     */
    public void setIterations(int iterations) {
        this.iterations = iterations;
        fire();
    }

    /**
     * Getter on the coloration mode
     * @return the current coloration mode
     */
    public SettingsController.ColorationMode getColorationMode() {
        return colorationMode;
    }

    /**
     * Change the current coloration mode of the current fractal
     * MVC: fires an event
     * @param colorationMode new coloration mode
     */
    public void setColorationMode(SettingsController.ColorationMode colorationMode) {
        this.colorationMode = colorationMode;
        fire();
    }

    /**
     * Get the current number of threads used
     * @return the current number of threads used
     */
    public int getThreadCount() {
        return threadCount;
    }

    /**
     * Update the current number of threads used
     * MVC: does not fire an event
     * @param threadCount the new number of threads used
     */
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
