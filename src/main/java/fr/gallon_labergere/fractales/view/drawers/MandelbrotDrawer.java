package fr.gallon_labergere.fractales.view.drawers;

import fr.gallon_labergere.fractales.model.Settings;

import java.awt.*;

public class MandelbrotDrawer implements IFractaleDrawer {
    @Override
    public void draw(Graphics g, int width, int height, Settings settings) {
        g.setColor(Color.WHITE);
        g.drawLine(settings.getCenterX(), -1000, settings.getCenterX(), 1000);
        g.drawLine(-1000, settings.getCenterY(), 1000, settings.getCenterY());
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
