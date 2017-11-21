package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public class MandelbrotDrawer implements IFractaleDrawer {

    private final float MIN_X;
    private final float MAX_X;
    private final float MIN_Y;
    private final float MAX_Y;

    public MandelbrotDrawer(float minX, float minY, float maxX, float maxY) {
        MIN_X = minX;
        MAX_X = maxX;
        MIN_Y = minY;
        MAX_Y = maxY;
    }

    @Override
    public void draw(Graphics g, int width, int height, Settings settings) {
        g.setColor(Color.WHITE);
        g.drawLine(settings.getCenterX(), -1000, settings.getCenterX(), 1000);
        g.drawLine(-1000, settings.getCenterY(), 1000, settings.getCenterY());
        float mapRangeX = MAX_X - MIN_X;
        float mapRangeY = MAX_Y - MIN_Y;

        System.out.println("Drawing from (" + settings.getMapX(0) + " ; " + settings.getMapY(0) + ") to ("
            + settings.getMapX(width) + " ; " + settings.getMapY(height) + ")");
//        int vxmin = settings.getMapX(0);
//        int vymax = settings.getMapY(height);
//        for (int y = 0; y < height; y++) {
//            int vy = settings.getMapY(y);
//            int b = vymax - vy;
//            for (int x = 0; x < width; x++) {
//                int vx = settings.getMapX(x);
//                int a = vx + vxmin;
//                int tx = 0, ty = 0, n = 0;
//                do {
//                    int temp = tx * tx - ty * ty + a;
//                    ty = 2 * tx * ty + b;
//                    tx = temp;
//                    n++;
//                } while (tx * tx + ty * ty < 4 && n <= 100);
//                g.setColor(n == 101 ? Color.BLACK : new Color((n / 100f), 0f, 0f));
//                g.fillRect(x, y, 1, 1);
//            }
//        }
//        g.setColor(Color.WHITE);
        g.drawLine(settings.getCenterX(), -1000, settings.getCenterX(), 1000);
        g.drawLine(-1000, settings.getCenterY(), 1000, settings.getCenterY());
    }
}
