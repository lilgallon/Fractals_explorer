package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlPanel extends SettingsObserver {

    private JComboBox fractalTypeSelection;
    private JLabel zoomLabel;
    private JProgressBar progressBar;
    private JSlider iterationsSlider;

    public ControlPanel(SettingsController controller) {
        super(controller);

        JPanel[][] grid = new JPanel[20][2];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                JPanel holder = new JPanel();
                holder.setBorder(new EmptyBorder(5, 10, 5, 10));
                holder.setLayout(new GridBagLayout());
                add(grid[i][j] = holder);
            }
        }

        setBorder(BorderFactory.createTitledBorder("Contrôles"));
        setLayout(new GridLayout(grid.length, grid[0].length, 5, 5));

        grid[0][0].add(new JLabel("Type de fractale"));
        grid[0][1].add(fractalTypeSelection = new JComboBox<>(SettingsController.FractalType.values()));
        fractalTypeSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                getSettingsController().setFractalType(
                        (SettingsController.FractalType) e.getItem()
                );
        });
        fractalTypeSelection.setSelectedItem(null);

        grid[2][0].add(new JLabel("Zoom"));
        grid[2][1].add(zoomLabel = new JLabel("(x" + getSettings().getZoomLevel() + ")"));
        JButton zoomOut = new JButton("-");
        zoomOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getSettingsController().zoomOut(e.getX(), e.getY());
            }
        });
        JButton zoomIn = new JButton("+");
        zoomIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getSettingsController().zoomIn(e.getX(), e.getY());
            }
        });
        grid[3][0].add(zoomOut);
        grid[3][1].add(zoomIn);

        progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        grid[4][0].add(new JLabel("Progression:"));
        grid[4][1].add(progressBar);

        iterationsSlider = new JSlider();
        iterationsSlider.setMaximum(Settings.MAX_ITERATIONS);
        iterationsSlider.setMinimum(Settings.MIN_ITERATIONS);
        iterationsSlider.setMajorTickSpacing(25);
        iterationsSlider.setMinorTickSpacing(5);
        iterationsSlider.setPaintTicks(true);
        final JLabel iterationsLabel = new JLabel("[" + getSettings().getIterations() + "] Iterations");
        iterationsSlider.addChangeListener(e -> {
            int val = iterationsSlider.getValue();
            controller.changeIteration(val);
            iterationsLabel.setText("[" + getSettings().getIterations() + "] Iterations");
        });
        grid[5][0].add(iterationsLabel);
        grid[5][1].add(iterationsSlider);

    }

    @Override
    void update(float zoomLevel, SettingsController.FractalType fractalType, int x, int y) {
        zoomLabel.setText("(x" + zoomLevel + ")");
        fractalTypeSelection.setSelectedItem(fractalType);
        progressBar.setValue(getSettings().getProgression());
    }
}
