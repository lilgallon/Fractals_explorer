package fr.gallon_labergere.fractales;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.ControlPanel;
import fr.gallon_labergere.fractales.view.ViewPanel;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private static Window instance;

    private Settings settings;
    private SettingsController settingsController;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    private Window() {
        instance = this;

        settings = new Settings(1f, SettingsController.FractalType.MANDELBROT);
        settingsController = new SettingsController(settings);

        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setTitle("Projet Java - Fractales - Lilian Gallon, Rémi Labergère");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel root = (JPanel) getContentPane();
        root.setLayout(new BorderLayout());
        root.add(new ControlPanel(settingsController), BorderLayout.WEST);
        root.add(new ViewPanel(settingsController), BorderLayout.CENTER);
        setVisible(true);
    }

    public static Window getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Window();
    }
}
