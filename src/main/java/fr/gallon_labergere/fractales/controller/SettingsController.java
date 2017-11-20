package fr.gallon_labergere.fractales.controller;

import fr.gallon_labergere.fractales.model.Settings;
import fr.gallon_labergere.fractales.view.drawers.IFractaleDrawer;
import fr.gallon_labergere.fractales.view.drawers.MandelbrotDrawer;
import fr.gallon_labergere.fractales.view.drawers.OtherDrawer;

public class SettingsController {

    private Settings settingsModel;

    public SettingsController(Settings settingsModel) {
        this.settingsModel = settingsModel;
    }

    public Settings getSettingsModel() {
        return settingsModel;
    }

    public void zoomIn(int x, int y) {
        if (settingsModel.getZoomLevel() >= Settings.MAX_ZOOM)
            return;
        int prevX = settingsModel.getViewX(x);
        int prevY = settingsModel.getViewY(y);
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() + Settings.ZOOM_STEP);
        int newX = settingsModel.getViewX(x);
        int newY = settingsModel.getViewY(y);
        move((int) ((newX - prevX) * settingsModel.getZoomLevel()), (int) ((newY - prevY) * settingsModel.getZoomLevel()));
    }

    public void zoomOut(int x, int y) {
        if (settingsModel.getZoomLevel() <= Settings.MIN_ZOOM)
            return;
        int prevX = settingsModel.getViewX(x);
        int prevY = settingsModel.getViewY(y);
        settingsModel.setZoomLevel(settingsModel.getZoomLevel() - Settings.ZOOM_STEP);
        int newX = settingsModel.getViewX(x);
        int newY = settingsModel.getViewY(y);
        move((int) ((newX - prevX) * settingsModel.getZoomLevel()), (int) ((newY - prevY) * settingsModel.getZoomLevel()));
    }

    public void setFractaleType(FractaleType fractaleType) {
        settingsModel.setFractaleType(fractaleType);
    }

    public void move(int dx, int dy) {
        settingsModel.setCenterX(settingsModel.getCenterX() + dx);
        settingsModel.setCenterY(settingsModel.getCenterY() + dy);
    }

    public enum FractaleType {
        MANDELBROT(new MandelbrotDrawer()),
        OTHER(new OtherDrawer());

        private IFractaleDrawer drawer;

        FractaleType(IFractaleDrawer drawer) {
            this.drawer = drawer;
        }

        public IFractaleDrawer getDrawer() {
            return drawer;
        }
    }
}
