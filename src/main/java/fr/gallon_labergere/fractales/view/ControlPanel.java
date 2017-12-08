package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
 * The settings are here
 */
public class ControlPanel extends SettingsObserver {

    private static JLabel progressBarLabel;
    private JComboBox fractalTypeSelection;
    private JLabel zoomLabel;
    private static JProgressBar progressBar;
    private JSlider iterationsSlider;
    private JLabel iterationsLabel;
    private JComboBox colorationModeSelection;
    private JSlider threadSlider;
    private JLabel threadLabel;

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

        // Fractal selector
        grid[0][0].add(new JLabel("Type de fractale"));
        grid[0][1].add(fractalTypeSelection = new JComboBox<>(SettingsController.FractalType.values()));
        fractalTypeSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                getSettingsController().setFractalType(
                        (SettingsController.FractalType) e.getItem()
                );
        });
        fractalTypeSelection.setSelectedItem(null);

        // Coloration mode selector
        grid[1][0].add(new JLabel("Mode de coloration"));
        grid[1][1].add(colorationModeSelection = new JComboBox<>(SettingsController.ColorationMode.values()));
        colorationModeSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                getSettingsController().changeColorationMode(
                        (SettingsController.ColorationMode) e.getItem()
                );
        });
        colorationModeSelection.setSelectedItem(SettingsController.ColorationMode.ORIGINAL);

        // Zoom controls
        grid[3][0].add(new JLabel("Zoom"));
        grid[3][1].add(zoomLabel = new JLabel("(x" + Float.toString((getSettings().getZoomLevel()-getSettings().getFractalType().getDrawer().getInitialZoom())/getSettings().getZoomLevel()-getSettings().getFractalType().getDrawer().getZoomFactor()) + ")"));
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
        grid[4][0].add(zoomOut);
        grid[4][1].add(zoomIn);

        // Iteration selector
        iterationsSlider = new JSlider();
        iterationsSlider.setMaximum(getSettings().getFractalType().getDrawer().getMaxIterations());
        iterationsSlider.setMinimum(getSettings().getFractalType().getDrawer().getMinIterations());
        iterationsLabel = new JLabel("Precision : [" + getSettings().getIterations() + "] Iteration(s)");
        iterationsSlider.addChangeListener(e -> {
            controller.changeIterations(iterationsSlider.getValue());
            iterationsLabel.setText("Precision : [" + getSettings().getIterations() + "] Iteration(s)");
        });
        grid[6][0].add(iterationsLabel);
        grid[6][1].add(iterationsSlider);

        // Thread number selector
        threadSlider = new JSlider(SettingsController.MIN_THREAD_COUNT, SettingsController.MAX_THREAD_COUNT, SettingsController.MIN_THREAD_COUNT);
        threadLabel = new JLabel("Multithreading : [" + threadSlider.getValue() + "] Thread(s)");
        threadSlider.addChangeListener(e -> {
            controller.setThreadCount(threadSlider.getValue());
            threadLabel.setText("Multithreading : [" + threadSlider.getValue() + "] Thread(s)");
        });
        grid[7][0].add(threadLabel);
        grid[7][1].add(threadSlider);

        // Progress bar
        progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        progressBarLabel = new JLabel("Progression (0%)");
        grid[19][0].add(progressBarLabel);
        grid[19][1].add(progressBar);
    }

    /**
     * Update the progression
     * @param val new progress value in percent
     */
    public static void setProgression(int val){
        progressBarLabel.setText("Progression (" + val + "%)");
        progressBar.setValue(val);
    }

    /**
     * Getter on the current progression
     * @return the current progression
     */
    public static int getProgression(){
        return progressBar.getValue();
    }


    /**
     * When the model fires a change event, we need to synchronize controls with the new values
     * @param zoomLevel current zoom value
     * @param fractalType current fractal
     * @param x unnecessary in this case
     * @param y unnecessary in this case
     */
    @Override
    void update(float zoomLevel, SettingsController.FractalType fractalType, int x, int y) {
        zoomLabel.setText("(x" + zoomLevel + ")");
        fractalTypeSelection.setSelectedItem(fractalType);
        iterationsSlider.setValue(getSettings().getIterations());
        iterationsSlider.setMaximum(getSettings().getFractalType().getDrawer().getMaxIterations());
        iterationsSlider.setMinimum(getSettings().getFractalType().getDrawer().getMinIterations());
    }
}
