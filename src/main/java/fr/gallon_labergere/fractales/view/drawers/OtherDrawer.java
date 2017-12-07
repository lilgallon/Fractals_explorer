package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.utils.Complex;

import java.awt.*;
import java.awt.image.BufferedImage;


public class OtherDrawer implements IFractalDrawer {

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
    public void draw(BufferedImage image, int start_y, int heigth, Settings settingsModel, SettingsController settingsController) {
        float tolerance = 0.0001f;

        for (int x = 0; x < image.getWidth(); ++x) {
            for(int y = start_y; y < start_y + heigth; ++y) {
                double z_i = (y - settingsModel.getCenterY())/settingsModel.getZoomLevel();//(y * (yb - ya ) / (height -1) + ya ) ;//+ settingsModel.getCenterY() /settingsModel.getZoomLevel();
                double z_r = (x - settingsModel.getCenterX())/settingsModel.getZoomLevel();//(x * (xb - xa) / (height -1) + xa ) ;//+ settingsModel.getCenterX() /settingsModel.getZoomLevel();

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
