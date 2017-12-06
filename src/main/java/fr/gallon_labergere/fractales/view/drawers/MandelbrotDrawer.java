package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public class MandelbrotDrawer implements IFractalDrawer {

    /**
     * TODO: remarque sur des attributs inutiles ou pas?
     * Retirer les MIN / MAX etc... ?
     * Pour le moment pas sur
     */

    private final double MIN_X;
    private final double MAX_X;
    private final double MIN_Y;
    private final double MAX_Y;

    private final float INITIAL_ZOOM = 300;
    private final float MIN_ZOOM = 1f;
    private final float MAX_ZOOM = 10000f;
    private final float ZOOM_MULTIPLICATOR = 1.2f;

    private final int INITIAL_ITERATIONS = 25;
    private final int MIN_ITERATIONS = 25;
    private final int MAX_ITERATIONS = 500;

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
         * TODO: optimisation avec les iterations
         * Grosse opti à faire : avoir un faible it_max au début, pour ensuite le faire augmenter avec le zoom.
         * En effet, pas besoin d'être très précis quand on a pas zoom, on augmentera ainsi la précision en zoomant.
         */

        /**
         * TODO: IMPORTANT -> ZOOM
         * Il y a un probleme dans le zoom, on le voit d'autant plus que le zoom est fort.
         * Peu importe ce qu'on fait, le zoom va zoomer au CENTRE de la FRACTALE et non pas au CENTRE de l'IMAGE
         * Je sais pas trop d'où ça vient, c'est surement la méthode zoomIn et zoomOut qui move le centre pas correctement
         */

        // Maximum number of iteration before stopping the calculation by supposing that the suite is convergent.
        double it_max = settingsModel.getIterations();

        // Used to adjust the fractal initial position
        double xGap = -500;
        double yGap = -300;

        /**
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
        for(int x = 0 ; x<width ; ++x){
            for(int y = 0; y<height ; ++y){

                double c_r = (x + xGap - centerX) / zoom;
                double c_i = (y + yGap - centerY) / zoom ;

                double z_r = 0;
                double z_i = 0;
                double i = 0;

                do{
                    double tmp = z_r;                   // Ici on stocke z_r
                    z_r = z_r * z_r - z_i*z_i + c_r;    // Ici on calcule z_r au rang n+1
                    z_i = 2 * z_i * tmp + c_i;
                    ++i;
                }while (z_r*z_r + z_i*z_i < 4 && i < it_max);


                g.setColor(getColor((int)i,settingsModel.getColorationMode()));
                g.fillRect(x, y, 1, 1);

                /** TODO: barre de progression
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

                //settingsController.updateProgression((x+ settingsModel.getCenterX())*(y+ settingsModel.getCenterY()),(int)((fractal_width+width)*(fractal_height+height)));

            }
        }

        settingsController.resetProgression();
        g.setColor(Color.WHITE);
        g.drawLine(settingsModel.getCenterX(), -1000, settingsModel.getCenterX(), 1000);
        g.drawLine(-1000, settingsModel.getCenterY(), 1000, settingsModel.getCenterY());
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

    /**
     * Get the color of the pixel according to the number of iterations.
     * Method #1 inspired by https://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia#25816111
     * Method #x (other) is a basic one with a blue gradient
     * @param iterations
     * @return
     */
    private Color getColor(int iterations, SettingsController.ColorationMode colorMode){
        Color color;

        if(colorMode== SettingsController.ColorationMode.ORIGINAL) {
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
        }else if(colorMode==SettingsController.ColorationMode.BLUE){
            if(iterations==MAX_ITERATIONS){
                color = Color.black;
            }else{
                color = new Color(0,0,(iterations*255)/MAX_ITERATIONS);
            }
        }else{
            throw new NullPointerException("It should not happen! An exception must have been thrown before in the colorset method");
        }
    return color;
    }
}
