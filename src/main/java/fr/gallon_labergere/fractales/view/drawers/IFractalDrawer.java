package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import java.awt.image.BufferedImage;

/* This file is part of the JavaFractal project.
 *
 * JavaFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaFractal.  If not, see <http://www.gnu.org/licenses/>.
 * Authors : Lilian Gallon, Rémi Labergère
 */
public interface IFractalDrawer {

    /**
     * Draw the selected fractal
     *
     * Thread has to calculate from start_y coordinate to start_y + height
     *
     * @param image image on which the fractal will be drawn
     * @param start_y initial y coordinate when the thread have to begin its calculations
     * @param height number of y pixels that the thread has to calculate (=> height)
     * @param settingsModel model
     * @param settingsController controller
     */
    void draw(BufferedImage image, int start_y, int height, Settings settingsModel, SettingsController settingsController);

    // Zoom getters : the zoom settings are different according to the fractal
    float getInitialZoom();
    float getMinZoom();
    float getMaxZoom();
    float getZoomFactor();

    // Iteration getters : the iterations settings are different according to the fractal
    int getInitialIterations();
    int getMaxIterations();
    int getMinIterations();
}
