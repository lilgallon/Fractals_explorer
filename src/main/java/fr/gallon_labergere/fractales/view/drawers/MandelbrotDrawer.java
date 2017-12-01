package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import javax.swing.*;
import java.awt.*;

public class MandelbrotDrawer implements IFractalDrawer {

    /**
     * TODO:
     * Retirer les MIN / MAX etc... ?
     * Pour le moment pas sur, c'est peut-etre de la que vient le probleme de precision.
     */

    private final double MIN_X;
    private final double MAX_X;
    private final double MIN_Y;
    private final double MAX_Y;

    public MandelbrotDrawer(double minX, double maxX, double minY, double maxY) {
        MIN_X = minX;
        MAX_X = maxX;
        MIN_Y = minY;
        MAX_Y = maxY;
    }

    /**
     * Method that contain the fractal calculation algorithm
     * @param g graphics
     * @param width view width
     * @param height view height
     * @param settingsModel model
     * @param settingsController controller
     */
    @Override
    public void draw(Graphics g, int width, int height, Settings settingsModel, SettingsController settingsController) {

        /**
          * TODO:
         * Pour le lol on pourrait changer la couleur de la fractale bleu / vert / rouge, et
         * d'autres nuances (ça fait pro)
         */

        /**
         * TODO:
         * Grosse opti à faire : avoir un faible it_max au début, pour ensuite le faire augmenter avec le zoom.
         * En effet, pas besoin d'être très précis quand on a pas zoom, on augmentera ainsi la précision en zoomant.
         */

        // Maximum number of iteration before stopping the calculation by supposing that the suite is convergent.
        double it_max = 50;

        /**
         * TODO:
         * Améliorer le zoom (ça vaudrait mieux un zoom *= 2) parce que là ça n'a pas tellement de sens
         */
        // The default zoom is not correct for mandelbrot, here are the adjustments
        double zoom = settingsModel.getZoomLevel()*200+100;

        // For every pixel, we will calculate their colors according to if the suite is convergent or divergent
        // The intensity of the colors changes according to the rapidity of the suite to diverge.
        // width & height -> dimensions of the drawing area
        for(int x = 0 ; x<width ; ++x){
            for(int y = 0; y<height ; ++y){

                double c_r = ((double)x-settingsModel.getCenterX()/2) / zoom;
                double c_i = ((double)y-settingsModel.getCenterY()/2) / zoom;

                double z_r = 0;
                double z_i = 0;
                double i = 0;

                do{
                    double tmp = z_r;                   // Ici on stocke z_r
                    z_r = z_r * z_r - z_i*z_i + c_r;    // Ici on calcule z_r au rang n+1
                    z_i = 2 * z_i * tmp + c_i;
                    ++i;
                }while (z_r*z_r + z_i*z_i < 4 && i < it_max);

                if(i == it_max){
                    g.setColor(Color.BLACK);
                }else{
                    g.setColor(new Color(0,0,(int)(i*255/it_max)));
                }

                g.fillRect(x, y, 1, 1);

                /** TODO:
                 * 21/11/17
                 * Implement progressBar
                 *
                 * Note: FR psq c'est mieux
                 *  Il faut faire attention, on ne peut pas mettre la progression dans le modele
                 *  parce que dans ce cas, dès que la progression change, on fire un event de modification
                 *  et, ça demande de redessiner la vue, et puis ça recommence en boucle et puis stackoverflow
                 *  exception si l'ordi ne crash pas avant.
                 *  C'est pourquoi il faudrait implémenter la barre de progression differemment (si on l'implem)
                 *  Le code est pret, faut juste savoir la maniere dont on va le faire pour le faire de la moins
                 *  moche maniere possible. Sachant que de ne pas faire ça dans le modele quand on fait du MVC c'est
                 *  de l'irrespect.
                 *  Cordialement, lilian <3
                 **/

                //settingsController.updateProgression((x+ settingsModel.getCenterX())*(y+ settingsModel.getCenterY()),(int)((fractal_width+width)*(fractal_height+height)));
            }
        }

        settingsController.resetProgression();
        g.setColor(Color.WHITE);
        g.drawLine(settingsModel.getCenterX(), -1000, settingsModel.getCenterX(), 1000);
        g.drawLine(-1000, settingsModel.getCenterY(), 1000, settingsModel.getCenterY());
    }

}
