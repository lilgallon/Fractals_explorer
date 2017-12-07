package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotDrawer implements IFractalDrawer {

    /**
     * Method that contain the fractal calculation algorithm
     * @param image image
     * @param settingsModel model
     * @param settingsController controller
     */
    @Override
    public void draw(BufferedImage image, int start_y, int height, Settings settingsModel, SettingsController settingsController) {
        /*
         * TODO: optimisation avec les iterations
         * Grosse opti à faire : avoir un faible it_max au début, pour ensuite le faire augmenter avec le zoom.
         * En effet, pas besoin d'être très précis quand on a pas zoom, on augmentera ainsi la précision en zoomant.
         */

        /*
         * TODO: IMPORTANT -> ZOOM
         * Il y a un probleme dans le zoom, on le voit d'autant plus que le zoom est fort.
         * Peu importe ce qu'on fait, le zoom va zoomer au CENTRE de la FRACTALE et non pas au CENTRE de l'IMAGE
         * Je sais pas trop d'où ça vient, c'est surement la méthode zoomIn et zoomOut qui move le centre pas correctement
         */

        // Maximum number of iteration before stopping the calculation by supposing that the suite is convergent.
        double it_max = settingsModel.getIterations();

        /*
         * TODO: change resolution => redraw
         * QUand on change la résolution, il faut redessiner la fractale
         */

        // The default zoom is not correct for mandelbrot, here are the adjustments
        double zoom = settingsModel.getZoomLevel();//*settingsModel.getZoomLevel()*200+100;
        double centerX = settingsModel.getCenterX();
        double centerY = settingsModel.getCenterY();

        // For every pixel, we will calculate their colors according to if the suite is convergent or divergent
        // The intensity of the colors changes according to the rapidity of the suite to diverge.
        // width & height -> dimensions of the drawing area
        for (int x = 0 ; x < image.getWidth() ; ++x){
            for (int y = start_y; y < start_y + height; ++y){

                double c_r = (x - centerX) / zoom;
                double c_i = (y - centerY) / zoom ;

                double z_r = 0;
                double z_i = 0;
                double i = 0;

                do {
                    double tmp = z_r;                   // Ici on stocke z_r
                    z_r = z_r * z_r - z_i * z_i + c_r;    // Ici on calcule z_r au rang n+1
                    z_i = 2 * z_i * tmp + c_i;
                    ++i;
                } while (z_r * z_r + z_i * z_i < 4 && i < it_max);

                image.setRGB(x, y, getColor((int)i, settingsModel.getColorationMode()).getRGB());

                /* TODO: barre de progression
                 * 21/11/17
                 * Implement progressBar
                 *
                 * Note: FR psq c'est mieux
                 *  Il faut faire attention, on ne peut pas mettre la progression dans le model
                 *  parce que dans ce cas, dès que la progression change, on fire un event de modification
                 *  et, ça demande de redessiner la vue, et puis ça recommence en boucle et puis stackoverflow
                 *  exception si l'ordi ne crash pas avant.
                 *  C'est pourquoi il faudrait implémenter la barre de progression differemment (si on l'implem)
                 *  Le code est pret, faut juste savoir la maniere dont on va le faire pour le faire de la moins
                 *  moche maniere possible. Sachant que de ne pas faire ça dans le model quand on fait du MVC c'est
                 *  de l'irrespect.
                 *  Cordialement, lilian <3
                 **/

//                settingsController.updateProgression((x+ settingsModel.getCenterX())*(y+ settingsModel.getCenterY()),(int)((fractal_width+width)*(fractal_height+height)));

            }
        }

        settingsController.resetProgression();

//        g.setColor(Color.WHITE);
//        g.drawLine(settingsModel.getCenterX(), -1000, settingsModel.getCenterX(), 1000);
//        g.drawLine(-1000, settingsModel.getCenterY(), 1000, settingsModel.getCenterY());
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
        return 1000000000f;
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

    /**
     * Get the color of the pixel according to the number of iterations.
     * Method #1 inspired by https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia#25816111
     * Method #x (other) is a basic one with a blue gradient
     * @param iterations nombre d'iterations
     * @return couleur
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
