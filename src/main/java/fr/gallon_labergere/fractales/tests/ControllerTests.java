package fr.gallon_labergere.fractales.tests;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by lgallon on 22/11/17.
 */
public class ControllerTests {

    private Settings modele;
    private SettingsController controller;


    @Before
    public void init(){
        modele = new Settings(Settings.MIN_ZOOM, SettingsController.FractalType.MANDELBROT);
        controller = new SettingsController(modele);
    }

    @Test
    public void testZoom() {

        // Test zoom initialization
        assertEquals(Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom increment
        controller.zoomIn(0,0);
        assertEquals(modele.ZOOM_STEP+Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom decrement
        controller.zoomOut(0,0);
        assertEquals(Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom max
        for(int i=0; i<modele.MAX_ZOOM/modele.ZOOM_STEP+1 ; ++i)
            controller.zoomIn(0,0);
        assertEquals(modele.MAX_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom min
        for(int i=0; i<modele.MAX_ZOOM/modele.ZOOM_STEP+1 ; ++i)
            controller.zoomOut(0,0);
        assertEquals(modele.MIN_ZOOM,modele.getZoomLevel(),0f);

    }
}
