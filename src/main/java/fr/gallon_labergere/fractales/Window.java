package fr.gallon_labergere.fractales;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.ControlPanel;
import fr.gallon_labergere.fractales.view.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;

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
public class Window extends JFrame {

    private static Window instance;

    private Settings settings;
    private SettingsController settingsController;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private boolean instantiation = true;

    private JPanel viewPanel;

    private Window() {
        instance = this;

        settings = new Settings(SettingsController.FractalType.MANDELBROT.getDrawer().getInitialZoom(), SettingsController.FractalType.MANDELBROT);
        settingsController = new SettingsController(settings);

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setTitle("Projet Java S3D - Fractales - Lilian Gallon, Rémi Labergère");

        // Used to put the window in the center of the user screen
        setLocationRelativeTo(null);
        // When we click the cross, it closes the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JPanel root = (JPanel) getContentPane();
        root.setLayout(new BorderLayout());
        // Where we will change settings
        root.add(new ControlPanel(settingsController), BorderLayout.WEST);
        // Where we will see the fractal
        root.add(viewPanel = new ViewPanel(settingsController), BorderLayout.CENTER);

        setVisible(true);
        settings.setImage(new BufferedImage(viewPanel.getWidth(), viewPanel.getHeight(), BufferedImage.TYPE_INT_RGB));

        // We need to update the image when the window is resized
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                /*
                 * At the instantiation of the window, this event is fired because the window is getting a new size.
                 * BUT, if we recalculate the image at this moment, we will calculate coordinates that are not in the image bounds.
                 * Otherwise, we want to recalculate the image.
                 */
                if(!instantiation) {
                    settings.setImage(new BufferedImage(viewPanel.getWidth(), viewPanel.getHeight(), BufferedImage.TYPE_INT_RGB));
                    settingsController.recalculateImage();
                }else{
                    instantiation = false;
                }
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {

            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });
    }

    /**
     * Getter on the instance of this class
     * @return the instance of the window class
     */
    public static Window getInstance() {
        return instance;
    }

    /**
     * Getter on the view panel (on which the image of the fractal will be applied)
     * @return the view panel
     */
    public JPanel getViewPanel() {
        return viewPanel;
    }

    /**
     * Main!
     * @param args command arguments
     */
    public static void main(String[] args) {
        new Window();
    }
}
