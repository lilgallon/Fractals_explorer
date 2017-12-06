package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.utils.Complex;

import java.awt.*;


public class OtherDrawer implements IFractalDrawer {

    private final float MIN_X;
    private final float MAX_X;
    private final float MIN_Y;
    private final float MAX_Y;

    private final float INITIAL_ZOOM = 1;
    private final float MIN_ZOOM = 1f;
    private final float MAX_ZOOM = 1000f;
    private final float ZOOM_MULTIPLICATOR = 1.2f;

    private final int INITIAL_ITERATIONS = 25;
    private final int MIN_ITERATIONS = 25;
    private final int MAX_ITERATIONS = 500;

    public OtherDrawer(float minX, float minY, float maxX, float maxY) {
        MIN_X = minX;
        MAX_X = maxX;
        MIN_Y = minY;
        MAX_Y = maxY;
    }


    // newton fractal https://en.wikipedia.org/wiki/Newton_fractal
    private Complex fractal_function(Complex z){
        // f(z) = z * z * z - 1.0 => z^3 - 1.0
        return (z.times(z).times(z)).minus(-1.0);
    }

    private double module(Complex z){
        return Math.sqrt(z.getReal()*z.getReal()+z.getImag()*z.getImag());
    }

    private Complex fractal_function_d(Complex z){
        // f'(z) = z * z * 2 => z^2 * 3
        return (z.times(z)).scale(3);
    }

    @Override
    public void draw(Graphics g, int width, int height, Settings settingsModel, SettingsController settingsController) {
        g.setColor(Color.WHITE);
        g.drawLine(settingsModel.getCenterX(), -1000, settingsModel.getCenterX(), 1000);
        g.drawLine(-1000, settingsModel.getCenterY(), 1000, settingsModel.getCenterY());
        System.out.println("Drawing from (" + settingsModel.getMapX(0) + " ; " + settingsModel.getMapX(width) + ") and ("
                + settingsModel.getMapY(0) + " ; " + settingsModel.getMapY(height) + ")");

        float tolerance = 0.0001f;

        for (int x=0 ; x<width ; ++x){
            for(int y=0; y<height ; ++y){
                double z_i = (y - settingsModel.getCenterY())/settingsModel.getZoomLevel();//(y * (yb - ya ) / (height -1) + ya ) ;//+ settingsModel.getCenterY() /settingsModel.getZoomLevel();
                double z_r = (x - settingsModel.getCenterX())/settingsModel.getZoomLevel();//(x * (xb - xa) / (height -1) + xa ) ;//+ settingsModel.getCenterX() /settingsModel.getZoomLevel();

                Complex z = new Complex(z_r , z_i);

                int it = 0;
                Complex z1 = new Complex(-1000,-1000);

                do{
                    // dz => f'(z)
                    Complex dz = fractal_function_d(z);
                    // zn+1 = zn - f(zn)/f'(zn)
                    z1 = z.minus( fractal_function(z).divides(dz) );

                    // zn = zn+1
                    z = z1;

                    ++it;
                }while (module(fractal_function(z))>tolerance && it < settingsModel.getIterations());

                g.setColor(new Color(it%4*64, it%8*32,it%16*16));
                // z -> oo
                if(it==MAX_ITERATIONS) g.setColor(Color.red);

                g.fillRect(x,y,1,1);
            }
        }

    }

    @Override
    public float getInitialZoom() {
        return INITIAL_ZOOM;
    }

    @Override
    public float getMinZoom() {
        return MIN_ZOOM;
    }

    @Override
    public float getMaxZoom() {
        return MAX_ZOOM;
    }

    @Override
    public float getZoomFactor() {
        return ZOOM_MULTIPLICATOR;
    }

    @Override
    public int getInitialIterations() {
        return INITIAL_ITERATIONS;
    }

    @Override
    public int getMaxIterations() {
        return MAX_ITERATIONS;
    }

    @Override
    public int getMinIterations() {
        return MIN_ITERATIONS;
    }
}
