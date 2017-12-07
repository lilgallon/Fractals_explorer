package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TreeDrawer implements IFractalDrawer {

    private Settings settingsModel;
    private SettingsController settingsController;

    /**
     * Draws recursively a tree
     * Helped by https://rosettacode.org/wiki/Rosetta_Code
     * @param x1
     * @param y1
     * @param angle
     * @param depth
     * @param image
     */
    private void drawTree(int x1, int y1, double angle, int depth, BufferedImage image) {
        // If the deh is at 0, we hit the end
        if (depth == 0) return;

        // (x1,y1) : start point and (x2,y2) : end point of the line
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * settingsModel.getZoomLevel());
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * settingsModel.getZoomLevel());

        // Draw the current calculated line
        drawLine(x1, y1, x2, y2, getColor(depth), image);

        settingsController.updateProgression(depth,settingsModel.getIterations());

        // Draw the next left tree from the last end point
        drawTree(x2, y2, angle - 20, depth - 1 , image);
        // Draw the next right tree from the last end point
        drawTree(x2, y2, angle + 20, depth - 1 , image);
    }

    /**
     * Get the color of the pixel according to the number of iterations.
     * Method #1 inspired by https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia#25816111
     * Method #x (other) is a basic one with a blue gradient
     * @param depth depth
     * @return color
     */
    private int getColor(int depth) {

            Color color;

            if (settingsModel.getColorationMode() == SettingsController.ColorationMode.ORIGINAL) {
                int depth_fixed = depth % 16;

                switch (depth_fixed) {
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
            } else if (settingsModel.getColorationMode() == SettingsController.ColorationMode.BLUE) {
                color = new Color(0f, 0f, (float)depth / settingsModel.getIterations());
            } else throw new NullPointerException("It should not happen! An exception must have been thrown before in the colorset method");
            return color.getRGB();
    }


    /**
     * Returns the absolute value of val
     * @param val
     * @return
     */
    private int abs(int val){
        return (val<0) ? -val : val;
    }

    /**
     * Draws a line (AB) into a buffered image
     * http://members.chello.at/~easyfilter/bresenham.html
     * @param x1 x coordinate of A
     * @param y1 y coordinate of A
     * @param x2 x coordinate of B
     * @param y2 y coordinate of B
     * @param color color
     * @param image image
     */
    private void drawLine(int x1, int y1, int x2, int y2, int color, BufferedImage image){

        int dx =  abs(x1-x2), sx = x2<x1 ? 1 : -1;
        int dy = -abs(y1-y2), sy = y2<y1 ? 1 : -1;
        int err = dx+dy, e2; /* error value e_xy */

        for(;;){  /* loop */
            if(!(x2<0 || x2>image.getWidth()-1 || y2<0 || y2>image.getHeight()-1)){
                image.setRGB(x2, y2, color);
            }
            if (x2==x1 && y2==y1) break;
            e2 = 2*err;
            if (e2 >= dy) { err += dy; x2 += sx; } /* e_xy+e_x > 0 */
            if (e2 <= dx) { err += dx; y2 += sy; } /* e_xy+e_y < 0 */
        }
    }

    private void clearImage(BufferedImage image){
        for(int x = 0 ; x<image.getWidth() ; ++x){
            for(int y = 0 ; y<image.getHeight(); ++y){
                image.setRGB(x,y,Color.black.getRGB());
            }
        }
    }

    @Override
    public void draw(BufferedImage image, int start_y, int height, Settings settingsModel, SettingsController settingsController) {
        this.settingsModel = settingsModel;
        this.settingsController = settingsController;
        clearImage(image);
        drawTree(settingsModel.getCenterX(), settingsModel.getCenterY(), -90, settingsModel.getIterations(),image);
        settingsController.resetProgression();
    }

    @Override
    public float getInitialZoom() {
        return 8f;
    }

    @Override
    public float getMinZoom() {
        return 1f;
    }

    @Override
    public float getMaxZoom() {
        return 200f;
    }

    @Override
    public float getZoomFactor() {
        return 1.05f;
    }

    @Override
    public int getInitialIterations() {
        return 9;
    }

    @Override
    public int getMaxIterations() {
        return 20;
    }

    @Override
    public int getMinIterations() {
        return 5;
    }
}
