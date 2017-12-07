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

public class Window extends JFrame {

    private static Window instance;

    private Settings settings;
    private SettingsController settingsController;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private boolean instanciation = true;

    private JPanel viewPanel;

    private Window() {
        instance = this;

        settings = new Settings(1f, SettingsController.FractalType.MANDELBROT);
        settingsController = new SettingsController(settings);
        settingsController.setZoom(settings.getFractalType().getDrawer().getInitialZoom());

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setTitle("Projet Java - Fractales - Lilian Gallon, Rémi Labergère");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel root = (JPanel) getContentPane();
        root.setLayout(new BorderLayout());
        root.add(new ControlPanel(settingsController), BorderLayout.WEST);
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
                if(!instanciation) {
                    settings.setImage(new BufferedImage(viewPanel.getWidth(), viewPanel.getHeight(), BufferedImage.TYPE_INT_RGB));
                    settingsController.recalculateImage();
                }else{
                    instanciation = false;
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

    public static Window getInstance() {
        return instance;
    }

    public JPanel getViewPanel() {
        return viewPanel;
    }

    public static void main(String[] args) {
        new Window();
    }
}
