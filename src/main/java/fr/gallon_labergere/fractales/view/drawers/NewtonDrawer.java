package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.utils.Complex;

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
public class NewtonDrawer implements IFractalDrawer {

    @Override
    public void draw(BufferedImage image, int start_y, int height, Settings settingsModel, SettingsController settingsController) {
        float tolerance = 0.0001f;

        for (int x = 0; x < image.getWidth(); ++x) {
            for(int y = start_y; y < start_y + height; ++y) {
                double z_i = (y - settingsModel.getCenterY())/settingsModel.getZoomLevel();
                double z_r = (x - settingsModel.getCenterX())/settingsModel.getZoomLevel();

                Complex z = new Complex(z_r , z_i);

                int it = 0;
                Complex z1;

                do {
                    // dz => f'(z)
                    Complex dz = fractal_function_d(z);
                    // zn+1 = zn - f(zn)/f'(zn)
                    z1 = z.minus(fractal_function(z).divides(dz));

                    // zn = zn+1
                    z = z1;

                    ++it;
                } while (module(fractal_function(z)) > tolerance && it < settingsModel.getIterations());

                // z -> oo
                if (it == getMaxIterations())
                    image.setRGB(x, y, Color.RED.getRGB());
                else
                    image.setRGB(x, y, new Color(it % 4 * 64, it % 8 * 32, it % 16 * 16).getRGB());
            }
        }
    }

    // newton fractal https://en.wikipedia.org/wiki/Newton_fractal
    private Complex fractal_function(Complex z) {
        // f(z) = z * z * z - 1.0 => z^3 - 1.0
        return (z.times(z).times(z)).minus(-1.0);
    }

    private double module(Complex z) {
        return Math.sqrt(z.getReal() * z.getReal() + z.getImag() * z.getImag());
    }

    private Complex fractal_function_d(Complex z) {
        // f'(z) = z * z * 2 => z^2 * 3
        return (z.times(z)).scale(3);
    }

    @Override
    public float getInitialZoom() {
        return 1f;
    }

    @Override
    public float getMinZoom() {
        return 1f;
    }

    @Override
    public float getMaxZoom() {
        return 1000f;
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
        return 500;
    }

    @Override
    public int getMinIterations() {
        return 25;
    }
}
