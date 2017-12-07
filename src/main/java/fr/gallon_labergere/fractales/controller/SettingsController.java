package fr.gallon_labergere.fractales.controller;

import fr.gallon_labergere.fractales.Window;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.ControlPanel;
import fr.gallon_labergere.fractales.view.drawers.IFractalDrawer;
import fr.gallon_labergere.fractales.view.drawers.MandelbrotDrawer;
import fr.gallon_labergere.fractales.view.drawers.NewtonDrawer;
import fr.gallon_labergere.fractales.view.drawers.TreeDrawer;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SettingsController {

    public static final int MIN_THREAD_COUNT = 1;
    public static final int MAX_THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    private int threadCount = MIN_THREAD_COUNT;

    private Settings settingsModel;

    private ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

    /**
     * Used to prevent the model to fire a change event.
     * It is useful to use shouldCalculateImage when multiple attributes of the model are changed but
     * we don't need it the view to be update (=> fractal)
     * ie: when changing the fractal we have the zoom, the center position, the iteration that are changed, but
     * we don't want to update the view every time we change an attribute, but when all of these are changed
     */
    private boolean shouldCalculateImage;

    public SettingsController(Settings settingsModel) {
        this.settingsModel = settingsModel;
        this.shouldCalculateImage = true;
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

        this.shouldCalculateImage = false;
        settingsModel.setFractalType(fractalType);
        settingsModel.setCenterX(fractalType.getxCenter());
        settingsModel.setCenterY(fractalType.getyCenter());
        settingsModel.setIterations(fractalType.getDrawer().getInitialIterations());
        settingsModel.setZoomLevel(fractalType.getDrawer().getInitialZoom());
        this.shouldCalculateImage = true;

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

        if(current>aimed)
            throw new IllegalArgumentException("The current value is bigger than the aimed value.");
        if(current<0)
            throw new IllegalArgumentException("The current value is negative.");
        if(aimed<0)
            throw new IllegalArgumentException("The aimed value is negative.");

        int progress = (current * 100) / aimed;

        // We update only if the progression has changed
        /*shouldCalculateImage = false;
        if (progress > settingsModel.getProgression())
            settingsModel.setProgression(progress);
        shouldCalculateImage = true;*/
        if(progress > ControlPanel.getProgression())
            ControlPanel.setProgression(progress);
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
        ControlPanel.setProgression(0);
    }

    public void recalculateImage() {
        if ((settingsModel.getImage() == null) ||!shouldCalculateImage) return;
        JPanel viewPanel = Window.getInstance().getViewPanel();
        if (settingsModel.getFractalType() == null) {
            JOptionPane.showMessageDialog(viewPanel.getParent(), "No fractal selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int height = viewPanel.getHeight();
        if(height!=getSettingsModel().getImage().getHeight()) height = getSettingsModel().getImage().getHeight();

        // Since the tree builder algorithm is recursive, using a multithreading system is slowing it
        if(threadCount>1 && getSettingsModel().getFractalType() == FractalType.TREE) threadCount = 1;

        executorService.shutdownNow();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService = Executors.newFixedThreadPool(threadCount);
        int heightPart = height / threadCount;

        for (int i = 0; i < threadCount; i++) {
            final int start_y = heightPart * i;
            executorService.execute(() -> {
                settingsModel.getFractalType().getDrawer().draw(settingsModel.getImage(), start_y, heightPart, settingsModel, this);
                settingsModel.fire();
            });
        }
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setShouldCalculateImage(boolean b){
        this.shouldCalculateImage = b;
    }

    /**
     * Fractal list
     */
    public enum FractalType {
        MANDELBROT(new MandelbrotDrawer(), 500, 300),
        NEWTON(new NewtonDrawer(), 0, 0),
        TREE(new TreeDrawer(),400,500);

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
