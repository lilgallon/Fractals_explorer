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
    public void draw(Graphics g, int width, int height, Settings settingsModele, SettingsController settingsController) {
        g.setColor(Color.WHITE);
        g.drawLine(settingsModele.getCenterX(), -1000, settingsModele.getCenterX(), 1000);
        g.drawLine(-1000, settingsModele.getCenterY(), 1000, settingsModele.getCenterY());
        System.out.println("Drawing from (" + settingsModele.getMapX(0) + " ; " + settingsModele.getMapX(width) + ") and ("
                + settingsModele.getMapY(0) + " ; " + settingsModele.getMapY(height) + ")");
    }
}
