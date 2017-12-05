package fr.gallon_labergere.fractales.controller;

import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.drawers.IFractalDrawer;
import fr.gallon_labergere.fractales.view.drawers.MandelbrotDrawer;
import fr.gallon_labergere.fractales.view.drawers.OtherDrawer;

public class SettingsController {

    private Settings settingsModel;

    public SettingsController(Settings settingsModel) {
        this.settingsModel = settingsModel;
    }

    public Settings getSettingsModel() {
        return settingsModel;
    }

    /**
     * Sets the zoom to a custom one
     * @param zoom zoomlevel
     */
    public void setZoom(float zoom){
        if(zoom<Settings.MIN_ZOOM){
            zoom = Settings.MIN_ZOOM;
        }else if(zoom>Settings.MAX_ZOOM){
            zoom = Settings.MAX_ZOOM;
        }
        settingsModel.setZoomLevel(zoom);
    }
    /**
     * It zooms in according to the (x,y) point
     * @param x x point coordinate
     * @param y y point coordinate
     */
    public void zoomIn(int x, int y) {
        if (settingsModel.getZoomLevel() >= Settings.MAX_ZOOM)
            return;
        float prevX = settingsModel.getViewX(x);
        float prevY = settingsModel.getViewY(y);
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() + Settings.ZOOM_STEP);
        float newX = settingsModel.getViewX(x);
        float newY = settingsModel.getViewY(y);
        move((int) ((newX - prevX)* settingsModel.getZoomLevel()), (int) ((newY - prevY)* settingsModel.getZoomLevel()));

    }

    /**
     * It zooms out according to the (x,y) point
     * @param x x point coordinate
     * @param y y point coordinate
     */
    public void zoomOut(int x, int y) {
        if (settingsModel.getZoomLevel() <= Settings.MIN_ZOOM)
            return;
        float prevX = settingsModel.getViewX(x);
        float prevY = settingsModel.getViewY(y);
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() - Settings.ZOOM_STEP);
        float newX = settingsModel.getViewX(x);
        float newY = settingsModel.getViewY(y);
        move((int) ((newX - prevX) * settingsModel.getZoomLevel()), (int) ((newY - prevY) * settingsModel.getZoomLevel()));
    }

    /**
     * Change the current fractal to the new one
     * @param fractalType
     */
    public void setFractalType(FractalType fractalType) {
        settingsModel.setFractalType(fractalType);
    }

    /**
     * Move the fractal
     * @param dx horizontal gap
     * @param dy vertical gap
     */
    public void move(int dx, int dy) {
        settingsModel.setCenterX(settingsModel.getCenterX() + dx);
        settingsModel.setCenterY(settingsModel.getCenterY() + dy);
    }

    /**
     * Updates the progression according to the current index (current) and the final index (aimed)
     * @param current Current index
     * @param aimed Aimed index (final index)
     */
    public void updateProgression(int current, int aimed){
        /*
        if(current>aimed)
            throw new IllegalArgumentException("The current value is bigger than the aimed value.");
        if(current<0)
            throw new IllegalArgumentException("The current value is negative.");
        if(aimed<0)
            throw new IllegalArgumentException("The aimed value is negative.");
        */
        int progress = (current * 100) / aimed;

        // Don't update everytime!!! Only when there is an update to prevent stackoverflow
        // Each 10% we will update.
        if(progress>settingsModel.getProgression())
            settingsModel.setProgression(progress);
    }

    /**
     * Used to change the number of iteration i.e. precision
     * @param val
     */
    public void changeIteration(int val){
        if(val<Settings.MIN_ITERATIONS){
            val = Settings.MIN_ITERATIONS;
        }else if(val>Settings.MAX_ITERATIONS){
            val = Settings.MAX_ITERATIONS;
        }
        settingsModel.setIterations(val);
    }

    public void changeColorationMode(ColorationMode mode){
       settingsModel.setColorationMode(mode);
    }

    /**
     * Reset the bar progression
     */
    public void resetProgression() {
        settingsModel.setProgression(0);
    }

    /**
     * Fractal list
     */
    public enum FractalType {
        // The Mandelbrot suite is always between -2.1 and 0.6 on the abscissa axis and between -1.2 and 1.2 on the ordinate axis.
        MANDELBROT(new MandelbrotDrawer(-2.1, 0.6, -1.2, 1.2)),
        OTHER(new OtherDrawer(0, 0, 0, 0));

        private IFractalDrawer drawer;

        FractalType(IFractalDrawer drawer) {
            this.drawer = drawer;
        }

        public IFractalDrawer getDrawer() {
            return drawer;
        }
    }

    public enum ColorationMode {
        ORIGINAL, BLUE
    }
}
