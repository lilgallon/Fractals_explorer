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
        modele = new Settings(Settings.MIN_ZOOM, null);
        controller = new SettingsController(modele);
    }

    @Test
    public void testZoom() {

        // Test zoom initialization
        assertEquals(Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom increment
        controller.zoomIn(0,0);
        assertEquals(Settings.ZOOM_STEP +Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom decrement
        controller.zoomOut(0,0);
        assertEquals(Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom max (zoomIn)
        for(int i = 0; i< Settings.MAX_ZOOM / Settings.ZOOM_STEP +1 ; ++i)
            controller.zoomIn(0,0);
        assertEquals(Settings.MAX_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom min (zoomOut)
        for(int i = 0; i< Settings.MAX_ZOOM / Settings.ZOOM_STEP +1 ; ++i)
            controller.zoomOut(0,0);
        assertEquals(Settings.MIN_ZOOM,modele.getZoomLevel(),0f);

        // Test zoom min (setZoom)
        controller.setZoom(Settings.MIN_ZOOM-5);
        assertEquals(modele.getZoomLevel(),Settings.MIN_ZOOM,0f);
        controller.setZoom(Settings.MAX_ZOOM+5);
        assertEquals(modele.getZoomLevel(),Settings.MAX_ZOOM,0f);
        controller.setZoom(Settings.MIN_ZOOM+5);
        assertEquals(modele.getZoomLevel(),Settings.MIN_ZOOM+5,0f);
    }

    @Test
    public void testChangeFractal(){
        // By default, no fractal selected
        assertEquals(modele.getFractalType()==null,true);

        // Change fractal
        controller.setFractalType(SettingsController.FractalType.MANDELBROT);
        assertEquals(modele.getFractalType()== SettingsController.FractalType.MANDELBROT,true);

    }

    @Test
    public void testCoordinateMapping(){
        // Test default coordinates
        assertEquals(modele.getCenterX()==0f,true);
        assertEquals(modele.getCenterY()==0f,true);

        // Test mapping methods
        // Basic tests
        assertEquals(modele.getMapX(modele.getCenterX())==0f,true);
        assertEquals(modele.getMapY(modele.getCenterY())==0f,true);
        assertEquals(modele.getViewY(modele.getCenterY())==0f,true);
        assertEquals(modele.getViewX(modele.getCenterY())==0f,true);

        // Advanced tests
        // Move
        controller.setZoom(2);
        final int DX = 5;
        final int DY = 10;
        controller.move(DX,DY);
        assertEquals(modele.getCenterX()==DX,true);
        assertEquals(modele.getCenterY()==DY,true);

        // Mapping
        final int X = 5;
        final int Y = 10;
        assertEquals(modele.getMapX(X)==(X-modele.getCenterX())/modele.getZoomLevel(),true);
        assertEquals(modele.getMapY(Y)==(Y-modele.getCenterY())/modele.getZoomLevel(),true);
        assertEquals(modele.getViewX(X)==(X-modele.getCenterX())*modele.getZoomLevel(),true);
        assertEquals(modele.getViewY(Y)==(Y-modele.getCenterY())*modele.getZoomLevel(),true);

    }

    @Test
    public void testIterationsUpdate(){
        // Test min iterations change
        controller.changeIteration(Settings.MIN_ITERATIONS-50);
        assertEquals(modele.getIterations()==Settings.MIN_ITERATIONS,true);

        // Test max iterations change
        controller.changeIteration(Settings.MAX_ITERATIONS+50);
        assertEquals(modele.getIterations()==Settings.MAX_ITERATIONS,true);
    }

}
