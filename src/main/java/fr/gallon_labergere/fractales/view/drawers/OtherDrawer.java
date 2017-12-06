package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public class OtherDrawer implements IFractalDrawer {

    private final float MIN_X;
    private final float MAX_X;
    private final float MIN_Y;
    private final float MAX_Y;

    private final float INITIAL_ZOOM = 1;
    private final float MIN_ZOOM = 1f;
    private final float MAX_ZOOM = 2f;
    private final float ZOOM_MULTIPLICATOR = 1.5f;

    private final int INITIAL_ITERATIONS = 25;
    private final int MIN_ITERATIONS = 25;
    private final int MAX_ITERATIONS = 500;

    public OtherDrawer(float minX, float minY, float maxX, float maxY) {
        MIN_X = minX;
        MAX_X = maxX;
        MIN_Y = minY;
        MAX_Y = maxY;
    }

    @Override
    public void draw(Graphics g, int width, int height, Settings settingsModel, SettingsController settingsController) {
        g.setColor(Color.WHITE);
        g.drawLine(settingsModel.getCenterX(), -1000, settingsModel.getCenterX(), 1000);
        g.drawLine(-1000, settingsModel.getCenterY(), 1000, settingsModel.getCenterY());
        System.out.println("Drawing from (" + settingsModel.getMapX(0) + " ; " + settingsModel.getMapX(width) + ") and ("
                + settingsModel.getMapY(0) + " ; " + settingsModel.getMapY(height) + ")");
    }

    @Override
    public float getInitialZoom() {
        return INITIAL_ZOOM;
    }

    @Override
    public float getMinZoom() {
        return MIN_ZOOM;
    }

    @Override
    public float getMaxZoom() {
        return MAX_ZOOM;
    }

    @Override
    public float getZoomFactor() {
        return ZOOM_MULTIPLICATOR;
    }

    @Override
    public int getInitialIterations() {
        return INITIAL_ITERATIONS;
    }

    @Override
    public int getMaxIterations() {
        return MAX_ITERATIONS;
    }

    @Override
    public int getMinIterations() {
        return MIN_ITERATIONS;
    }
}
