package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public class OtherDrawer implements IFractaleDrawer {
    @Override
    public void draw(Graphics g, int width, int height, Settings settings) {
        g.setColor(Color.WHITE);
        g.drawLine(settings.getCenterX(), -1000, settings.getCenterX(), 1000);
        g.drawLine(-1000, settings.getCenterY(), 1000, settings.getCenterY());
        System.out.println("Drawing from (" + settings.getMapX(0) + " ; " + settings.getMapX(width) + ") and ("
                + settings.getMapY(0) + " ; " + settings.getMapY(height) + ")");
    }
}
