package fr.gallon_labergere.fractales.view;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

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
public abstract class SettingsObserver extends JPanel implements Observer {

    private SettingsController settingsController;
    private Settings settings;

    SettingsObserver(SettingsController settingsController) {
        this.settingsController = settingsController;
        this.settings = settingsController.getSettingsModel();
        this.settings.addObserver(this);
    }

    /**
     * Getter on the model (settings)
     * @return the model
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Getter on the controller (settingsController)
     * @return the controller
     */
    SettingsController getSettingsController() {
        return settingsController;
    }

    /**
     * This method will be called whenever the Observable (model) spots a change.
     * @param o observable
     * @param arg object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Settings))
            throw new IllegalStateException("Update cannot be triggered by anything else than Settings");
        Settings settings = (Settings) o;
        update(settings.getZoomLevel(), settings.getFractalType(), settings.getCenterX(), settings.getCenterY());
    }

    /**
     * This method will be defined in inherited classes.
     * @param zoomLevel current zoom level
     * @param fractalType current fractal type
     * @param x current x pos
     * @param y current y pos
     */
    abstract void update(float zoomLevel, SettingsController.FractalType fractalType, int x, int y);
}
