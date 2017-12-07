package fr.gallon_labergere.fractales;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.ControlPanel;
import fr.gallon_labergere.fractales.view.ViewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame {

    private static Window instance;

    private Settings settings;
    private SettingsController settingsController;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

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
        System.out.println("vieww:"+viewPanel.getWidth() + " heightw"+ viewPanel.getHeight());
        settings.setImage(new BufferedImage(viewPanel.getWidth(), viewPanel.getHeight(), BufferedImage.TYPE_INT_RGB));
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
