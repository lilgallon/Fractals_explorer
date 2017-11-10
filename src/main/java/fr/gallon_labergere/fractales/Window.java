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

    private Window() {
        instance = this;

        settings = new Settings(1f, SettingsController.FractaleType.MANDELBROT);
        settingsController = new SettingsController(settings);

        setMinimumSize(new Dimension(1280, 720));
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
