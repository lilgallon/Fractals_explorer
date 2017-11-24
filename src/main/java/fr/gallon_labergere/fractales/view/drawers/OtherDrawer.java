package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public class OtherDrawer implements IFractalDrawer {

    private final float MIN_X;
    private final float MAX_X;
    private final float MIN_Y;
    private final float MAX_Y;

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
}
