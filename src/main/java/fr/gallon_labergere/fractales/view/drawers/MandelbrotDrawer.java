package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import javax.swing.*;
import java.awt.*;

public class MandelbrotDrawer implements IFractalDrawer {

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

    @Override
    public void draw(Graphics g, int width, int height, Settings settingsModel, SettingsController settingsController) {

//        float mapRangeX = MAX_X - MIN_X;
//        float mapRangeY = MAX_Y - MIN_Y;
//
//        System.out.println("Drawing from (" + settings.getMapX(0) + " ; " + settings.getMapY(0) + ") to ("
//            + settings.getMapX(width) + " ; " + settings.getMapY(height) + ")");
//        int vxmin = settings.getMapX(0);
//        int vymax = settings.getMapY(height);
//
//        // Pour chaque coordonnée Y, faire :
//        for (int y = 0; y < height; y++) {
//            int vy = settings.getMapY(y);
//            int b = vymax - vy;
//
//            // Pour chaque coordonnée X, faire :
//            for (int x = 0; x < width; x++) {
//                int vx = settings.getMapX(x);
//                int a = vx + vxmin; // a = c_i
//
//                int tx = 0, ty = 0, n = 0;
//                do {
//                    int temp = tx * tx - ty * ty + a; // temp = z_r (partie réelle)
//                    ty = 2 * tx * ty + b; // ty = z_i (partie imaginaire)
//                    tx = temp;
//                    n++;
//                } while (tx * tx + ty * ty < 4 && n <= 100);
//                g.setColor(n == 101 ? Color.BLACK : new Color((n / 100f), 0f, 0f));
//                g.fillRect(x, y, 1, 1);
//            }
//        }


        System.out.println("calcul" + settingsModel.getCenterX() + ";" + settingsModel.getCenterY() );
         /**
         * TODO:
         * 21/11/17
         *
         * 1)
         * On ne peut pas se déplacer dans la fractale, on peut zoomer, mais pas sur un point
         * précis, on reste sur (0,0).
         * Quand on se déplace, c'est en fait le rendu qui ne se fait que sur une certaine zone.
         * Je m'explique, quand on veut se déplacer avec le curseur, la zone dans laquelle sera
         * dessinée la fractale est déplacée, MAIS pas la fractale. C'est-à-dire qu'à force de
         * se déplacer, la fractale sera coupée.
         * Il faut trouver le truc qui fait qu'on peut déplacer la fractale (ça doit etre un truc
         * à la con enplus)
          *
          * update 28/11/17:
          * En fait il ne faut pas confondre les coordonnées de la fractale, et les coordonnées
          * où est-ce qu'on va les dessiner. Le problème se pose pas quand on ne zoom pas. Mais dès qu'on zoom
          * ces coordonnées ne sont pas liées.
         *
         * 2)
         * Pour le lol on pourrait changer la couleur de la fractale bleu / vert / rouge, et
         * d'autres nuances (ça fait pro)
         */

        // Maximum number of iteration before stopping the calculation by supposing that the suite is convergent.
        double it_max = 50;

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
