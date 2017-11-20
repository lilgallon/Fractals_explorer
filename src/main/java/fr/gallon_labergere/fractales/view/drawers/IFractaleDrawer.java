package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public interface IFractaleDrawer {
    void draw(Graphics g, int width, int height, Settings settings);
}
