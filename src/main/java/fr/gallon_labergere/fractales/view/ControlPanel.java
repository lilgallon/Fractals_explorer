package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlPanel extends SettingsObserver {

    private static JLabel progressBarLabel;
    private JComboBox fractalTypeSelection;
    private JLabel zoomLabel;
    private static JProgressBar progressBar;
    private JSlider iterationsSlider;
    private JLabel iterationsLabel;
    private JComboBox colorationModeSelection;
    private JSlider threadSlider;
    JLabel threadLabel;

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

        grid[1][0].add(new JLabel("Mode de coloration"));
        grid[1][1].add(colorationModeSelection = new JComboBox<>(SettingsController.ColorationMode.values()));
        colorationModeSelection.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED)
                getSettingsController().changeColorationMode(
                        (SettingsController.ColorationMode) e.getItem()
                );
        });
        colorationModeSelection.setSelectedItem(SettingsController.ColorationMode.ORIGINAL);

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

        iterationsSlider = new JSlider();
        iterationsSlider.setMaximum(getSettings().getFractalType().getDrawer().getMaxIterations());
        iterationsSlider.setMinimum(getSettings().getFractalType().getDrawer().getMinIterations());
        iterationsLabel = new JLabel("Precision : [" + getSettings().getIterations() + "] Iteration(s)");
        iterationsSlider.addChangeListener(e -> {
            controller.changeIteration(iterationsSlider.getValue());
            iterationsLabel.setText("Precision : [" + getSettings().getIterations() + "] Iteration(s)");
        });
        grid[6][0].add(iterationsLabel);
        grid[6][1].add(iterationsSlider);



        threadSlider = new JSlider(controller.MIN_THREAD_COUNT, controller.MAX_THREAD_COUNT, controller.MIN_THREAD_COUNT);
        threadLabel = new JLabel("Multithreading : [" + threadSlider.getValue() + "] Thread(s)");
        threadSlider.addChangeListener(e -> {
            controller.setThreadCount(threadSlider.getValue());
            threadLabel.setText("Multithreading : [" + threadSlider.getValue() + "] Thread(s)");
        });
        grid[7][0].add(threadLabel);
        grid[7][1].add(threadSlider);


        progressBar = new JProgressBar(0,100);
        progressBar.setValue(0);
        progressBarLabel = new JLabel("Progression (0%)");
        grid[19][0].add(progressBarLabel);
        grid[19][1].add(progressBar);



    }

    public static void setProgression(int val){
        progressBarLabel.setText("Progression (" + val + "%)");
        progressBar.setValue(val);
    }

    public static int getProgression(){
        return progressBar.getValue();
    }


    @Override
    void update(float zoomLevel, SettingsController.FractalType fractalType, int x, int y) {
        zoomLabel.setText("(x" + zoomLevel + ")");
        fractalTypeSelection.setSelectedItem(fractalType);
        //progressBar.setValue(getSettings().getProgression());
        iterationsSlider.setValue(getSettings().getIterations());
        iterationsSlider.setMaximum(getSettings().getFractalType().getDrawer().getMaxIterations());
        iterationsSlider.setMinimum(getSettings().getFractalType().getDrawer().getMinIterations());
    }
}
