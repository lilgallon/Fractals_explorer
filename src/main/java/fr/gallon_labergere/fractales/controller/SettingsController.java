package fr.gallon_labergere.fractales.controller;

import fr.gallon_labergere.fractales.Window;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.drawers.IFractalDrawer;
import fr.gallon_labergere.fractales.view.drawers.MandelbrotDrawer;
import fr.gallon_labergere.fractales.view.drawers.OtherDrawer;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SettingsController {

    public static final int MIN_THREAD_COUNT = 1;
    public static final int MAX_THREAD_COUNT = 8;

    private int threadCount = MIN_THREAD_COUNT;

    private Settings settingsModel;

    private ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

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
        if (zoom < settingsModel.getFractalType().getDrawer().getMinZoom()) {
            zoom = settingsModel.getFractalType().getDrawer().getMinZoom();
        } else if (zoom > settingsModel.getFractalType().getDrawer().getMaxZoom()) {
            zoom = settingsModel.getFractalType().getDrawer().getMaxZoom();
        }
        settingsModel.setZoomLevel(zoom);
        recalculateImage();
    }
    /**
     * It zooms in according to the (x,y) point
     * @param x x point coordinate
     * @param y y point coordinate
     */
    public void zoomIn(int x, int y) {
        if (settingsModel.getZoomLevel() * settingsModel.getFractalType().getDrawer().getZoomFactor() >= settingsModel.getFractalType().getDrawer().getMaxZoom())
            return;
        float prevMapX = settingsModel.getMapX(x);
        float prevMapY = settingsModel.getMapY(y);
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() * settingsModel.getFractalType().getDrawer().getZoomFactor());
        int newCursorX = settingsModel.getViewX(prevMapX);
        int newCursorY = settingsModel.getViewY(prevMapY);
        move(x - newCursorX, y - newCursorY); // Compensating cursor movement

    }

    /**
     * It zooms out according to the (x,y) point
     * @param x x point coordinate
     * @param y y point coordinate
     */
    public void zoomOut(int x, int y) {
        if (settingsModel.getZoomLevel()/settingsModel.getFractalType().getDrawer().getZoomFactor() <= settingsModel.getFractalType().getDrawer().getMinZoom())
            return;
        float prevMapX = settingsModel.getMapX(x);
        float prevMapY = settingsModel.getMapY(y);
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() / settingsModel.getFractalType().getDrawer().getZoomFactor());
        int newCursorX = settingsModel.getViewX(prevMapX);
        int newCursorY = settingsModel.getViewY(prevMapY);
        move(x - newCursorX, y - newCursorY); // Compensating cursor movement
    }

    /**
     * Change the current fractal to the new one
     * @param fractalType type of fractal
     */
    public void setFractalType(FractalType fractalType) {
        if (fractalType == null) throw  new NullPointerException("The fractal type must be defined!");
        settingsModel.setFractalType(fractalType);
        settingsModel.setCenterX(fractalType.getxCenter());
        settingsModel.setCenterY(fractalType.getyCenter());
        settingsModel.setZoomLevel(fractalType.getDrawer().getInitialZoom());
        recalculateImage();
    }

    /**
     * Move the fractal
     * @param dx horizontal gap
     * @param dy vertical gap
     */
    public void move(int dx, int dy) {
        settingsModel.setCenterX(settingsModel.getCenterX() + dx);
        settingsModel.setCenterY(settingsModel.getCenterY() + dy);
        recalculateImage();
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
        if (progress > settingsModel.getProgression())
            settingsModel.setProgression(progress);
    }

    /**
     * Used to change the number of iteration i.e. precision
     * @param val
     */
    public void changeIteration(int val){
        if (val < settingsModel.getFractalType().getDrawer().getMinIterations())
            val = settingsModel.getFractalType().getDrawer().getMinIterations();
        else if (val > settingsModel.getFractalType().getDrawer().getMaxIterations())
            val = settingsModel.getFractalType().getDrawer().getMaxIterations();
        settingsModel.setIterations(val);
        recalculateImage();
    }

    public void changeColorationMode(ColorationMode mode){
       if (mode == null) throw new NullPointerException("The color mode must be defined!");
       settingsModel.setColorationMode(mode);
       recalculateImage();
    }

    /**
     * Reset the bar progression
     */
    public void resetProgression() {
        settingsModel.setProgression(0);
    }

    public void recalculateImage() {
        if (settingsModel.getImage() == null) return;
        JPanel viewPanel = Window.getInstance().getViewPanel();
        if (settingsModel.getFractalType() == null) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "No fractal selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        executorService.shutdownNow();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService = Executors.newFixedThreadPool(threadCount);
        int heigthPart = viewPanel.getHeight() / threadCount;
        for (int i = 0; i < threadCount; i++) {
            final int start_y = heigthPart * i;
            executorService.execute(() -> {
                settingsModel.getFractalType().getDrawer().draw(settingsModel.getImage(), start_y, heigthPart, settingsModel, this);
                settingsModel.fire();
            });
        }
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    /**
     * Fractal list
     */
    public enum FractalType {
        MANDELBROT(new MandelbrotDrawer(), 500, 300),
        OTHER(new OtherDrawer(), 0, 0);

        private IFractalDrawer drawer;
        private int xCenter;
        private int yCenter;

        FractalType(IFractalDrawer drawer, int xCenter, int yCenter) {
            this.drawer = drawer;
            this.xCenter = xCenter;
            this.yCenter = yCenter;
        }

        public IFractalDrawer getDrawer() {
            return drawer;
        }

        public int getxCenter() {
            return xCenter;
        }

        public int getyCenter() {
            return yCenter;
        }
    }

    public enum ColorationMode {
        ORIGINAL, BLUE
    }
}
