package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface IFractalDrawer {

    /**
     * Draw the selected fractal
     * @param image image
     * @param settingsModel model
     * @param settingsController controller
     */
    void draw(BufferedImage image, int start_y, int height, Settings settingsModel, SettingsController settingsController);

    float getInitialZoom();
    float getMinZoom();
    float getMaxZoom();
    float getZoomFactor();

    int getInitialIterations();
    int getMaxIterations();
    int getMinIterations();
}
