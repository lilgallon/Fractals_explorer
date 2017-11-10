package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class ViewPanel extends SettingsObserver {

    public ViewPanel(SettingsController controller) {
        super(controller);
        setBackground(Color.BLACK);
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0)
                    controller.zoomOut();
                else
                    controller.zoomIn();
            }
        });
    }

    @Override
    void update(float zoomLevel, SettingsController.FractaleType fractaleType) {
        paintComponents(getGraphics());
    }

    @Override
    public void paintComponents(Graphics g) {
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.drawRect(10, 10, (int) (5 * getSettings().getZoomLevel()), (int) (5 * getSettings().getZoomLevel()));
    }
}
