package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 * It is the panel on which fractales will be drawn
 */
public class ViewPanel extends SettingsObserver {

    public ViewPanel(SettingsController controller) {
        super(controller);
        setBackground(Color.BLACK);
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0)
                    controller.zoomOut(e.getX(), e.getY());
                else
                    controller.zoomIn(e.getX(), e.getY());
            }
        });
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                System.out.println("Position centre fractale : (" + getSettings().getCenterX() + " ; " + getSettings().getCenterY() + ")");
                System.out.println("Position clic relatif au panel : (" + e.getX() + " ; " + e.getY() + ")");
                System.out.println("Position clic relatif à la vue : (" + getSettings().getViewX(e.getX()) + " ; " + getSettings().getViewY(e.getY()) + ")");
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            private int lastX = -1;
            private int lastY = -1;

            @Override
            public void mouseDragged(MouseEvent e) {
                if (getCursor().getType() == Cursor.MOVE_CURSOR && lastX != -1 && lastY != -1)
                    getSettingsController().move(e.getX() - lastX, e.getY() - lastY);
                lastX = e.getX();
                lastY = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });
    }

    /**
     * Updates the current fractale by repainting it.
     * @param zoomLevel zoom level on the fractale
     * @param fractalType selected fractale
     * @param x horizontal gap according to the center of the fractale
     * @param y vertical gap according to the center of the fracale
     */
    @Override
    void update(float zoomLevel, SettingsController.FractalType fractalType, int x, int y) {
        paintComponents(getGraphics());
    }

    /**
     * Paints the fractale
     * @param g
     */
    @Override
    public void paintComponents(Graphics g) {
        //g.fillRect(0, 0, getWidth(), getHeight());
        if(getSettings().getFractalType()!=null){
            getSettings().getFractalType().getDrawer().draw(g, getWidth(), getHeight(), getSettings(), getSettingsController());
        }
    }
}
