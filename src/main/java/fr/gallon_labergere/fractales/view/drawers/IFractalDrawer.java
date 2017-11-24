package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public interface IFractalDrawer {

    /**
     * Draw the selected fractal
     * @param g graphics
     * @param width view width
     * @param height view height
     * @param settingsModel model
     * @param settingsController controller
     */
    void draw(Graphics g, int width, int height, Settings settingsModel, SettingsController settingsController);
}
