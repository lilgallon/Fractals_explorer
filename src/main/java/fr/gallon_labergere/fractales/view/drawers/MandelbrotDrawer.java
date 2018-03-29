package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;
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
public class MandelbrotDrawer implements IFractalDrawer {

    @Override
    public void draw(BufferedImage image, int start_y, int height, Settings settingsModel, SettingsController settingsController) {

        // Maximum number of iteration before stopping the calculation by supposing that the suite is convergent.
        double it_max = settingsModel.getIterations();

        double zoom = settingsModel.getZoomLevel();
        double centerX = settingsModel.getCenterX();
        double centerY = settingsModel.getCenterY();

        // For every pixel, we will calculate their colors according to if the suite is convergent or divergent
        // The intensity of the colors changes according to the rapidity of the suite to diverge.
        // image.getWidth() & start_y + height -> dimensions of the drawing area
        for (int x = 0 ; x < image.getWidth() ; ++x){
            for (int y = start_y; y < start_y + height; ++y){

                // c_r -> x coordinate on where to start the suite and c_i same but for y
                double c_r = (x - centerX) / zoom;
                double c_i = (y - centerY) / zoom ;

                double z_r = 0;
                double z_i = 0;
                int it = 0;

                do {
                    // We get z_r at n
                    double tmp = z_r;

                    // We calculate z at n+1 (so without using complex we need to calculate imaginary and real part differently z_r and z_i)
                    z_r = z_r * z_r - z_i * z_i + c_r;
                    z_i = 2 * z_i * tmp + c_i;

                    // Increment the iteration
                    ++it;

                    // And we do all of this while the module of z is inferior to 2
                    // Reminder : module of z is sqrt(z_r^2+z_i^2)
                } while (z_r * z_r + z_i * z_i < 4 && it < it_max);

                image.setRGB(x, y, getColor(it, settingsModel.getColorationMode()).getRGB());
                settingsController.updateProgression(x*y,(image.getWidth() )*(start_y+height));
            }
        }
        settingsController.resetProgression();
    }

    @Override
    public float getInitialZoom() {
        return 300f;
    }

    @Override
    public float getMinZoom() {
        return 150f;
    }

    @Override
    public float getMaxZoom() {
        return 10000000000f;
    }

    @Override
    public float getZoomFactor() {
        return 1.2f;
    }

    @Override
    public int getInitialIterations() {
        return 25;
    }

    @Override
    public int getMaxIterations() {
        return 2000;
    }

    @Override
    public int getMinIterations() {
        return 25;
    }

    /**
     * Get the color of the pixel according to the number of iterations.
     * Method ORIGINAL inspired by https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia#25816111
     * Method BLUE is a basic one with a blue gradient
     * @param iterations number of iterations
     * @return color
     */
    private Color getColor(int iterations, SettingsController.ColorationMode colorMode){
        Color color;

        if ( colorMode == SettingsController.ColorationMode.ORIGINAL) {
            int iterations_fixed = iterations % 16;
            switch (iterations_fixed) {
                case 0:
                    color = new Color(60, 30, 15);
                    break;
                case 1:
                    color = new Color(25, 7, 26);
                    break;
                case 2:
                    color = new Color(9, 1, 47);
                    break;
                case 3:
                    color = new Color(4, 4, 73);
                    break;
                case 4:
                    color = new Color(0, 7, 100);
                    break;
                case 5:
                    color = new Color(12, 44, 138);
                    break;
                case 6:
                    color = new Color(24, 82, 117);
                    break;
                case 7:
                    color = new Color(57, 125, 209);
                    break;
                case 8:
                    color = new Color(134, 181, 229);
                    break;
                case 9:
                    color = new Color(211, 236, 248);
                    break;
                case 10:
                    color = new Color(241, 233, 191);
                    break;
                case 11:
                    color = new Color(248, 201, 95);
                    break;
                case 12:
                    color = new Color(255, 170, 0);
                    break;
                case 13:
                    color = new Color(204, 128, 0);
                    break;
                case 14:
                    color = new Color(153, 87, 0);
                    break;
                case 15:
                    color = new Color(106, 52, 3);
                    break;
                default:
                    color = Color.black;
                    break;
            }
        } else if (colorMode == SettingsController.ColorationMode.BLUE) {
            color = (iterations == getMaxIterations())
                    ? Color.black
                    : new Color(0f, 0f, (float)iterations / getMaxIterations());
        } else throw new NullPointerException("It should not happen! An exception must have been thrown before in the colorset method");
    return color;
    }
}
