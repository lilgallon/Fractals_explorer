package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/* This file is part of the JavaFractal project.
 *
 * JavaFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JavaFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaFractal.  If not, see <http://www.gnu.org/licenses/>.
 * Authors : Lilian Gallon, Rémi Labergère
 */

/**
 * It is the panel on which fractales will be drawn
 */
public class ViewPanel extends SettingsObserver {

    public ViewPanel(SettingsController controller) {
        super(controller);
        setBackground(Color.BLACK);

        // Handle the zoom on the cursor when using the wheel
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0)
                    controller.zoomOut(e.getX(), e.getY());
                else
                    controller.zoomIn(e.getX(), e.getY());
            }
        });

        // Handle the mouse grab and release to move the fractal
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
    void update(float zoomLevel, SettingsController.FractalType fractalType, long x, long y) {
        paintComponent(getGraphics());
    }

    /**
     * Paints the fractale
     * @param g Graphics used to paint the image on the panel
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(getSettings().getImage(), 0, 0, null);
    }
}
