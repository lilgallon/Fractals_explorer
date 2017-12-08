package fr.gallon_labergere.fractales.tests;

import fr.gallon_labergere.fractales.controller.SettingsController;
import fr.gallon_labergere.fractales.model.Settings;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
public class ControllerTests {

    private Settings model;
    private SettingsController controller;


    @Before
    public void init(){
        model = new Settings(0f, SettingsController.FractalType.MANDELBROT);
        controller = new SettingsController(model);
        model.setZoomLevel(model.getFractalType().getDrawer().getMinZoom());
    }

    @Test
    public void testZoom() {
        float min_zoom = model.getFractalType().getDrawer().getMinZoom();
        float max_zoom = model.getFractalType().getDrawer().getMaxZoom();
        float zoom_factor = model.getFractalType().getDrawer().getZoomFactor();

        // Test zoom initialization
        assertEquals(min_zoom, model.getZoomLevel(),0f);

        // Test zoom increment
        controller.zoomIn(0,0);
        assertEquals(zoom_factor * min_zoom, model.getZoomLevel(),0f);

        // Test zoom decrement
        controller.zoomOut(0,0);
        assertEquals(min_zoom<= model.getZoomLevel(),true);

        // Test zoom max (zoomIn)
        for(int i = 0; i< max_zoom / zoom_factor +1 ; ++i)
            controller.zoomIn(0,0);
        assertEquals(max_zoom>=model.getZoomLevel(),true);

        // Test zoom min (zoomOut)
        for(int i = 0; i< max_zoom / zoom_factor +1 ; ++i)
            controller.zoomOut(0,0);
        assertEquals(min_zoom<=model.getZoomLevel(),true);

        // Test zoom min (setZoom)
        controller.setZoom(min_zoom-5);
        assertEquals(model.getZoomLevel(),min_zoom,0f);
        controller.setZoom(max_zoom+5);
        assertEquals(model.getZoomLevel(),max_zoom,0f);
        controller.setZoom(min_zoom+5);
        assertEquals(model.getZoomLevel(),min_zoom+5,0f);
    }

    @Test
    public void testChangeFractal(){
        // By default, mandelbrot is selected
        assertEquals(model.getFractalType()==SettingsController.FractalType.MANDELBROT,true);

        // Change fractal
        controller.setFractalType(SettingsController.FractalType.TREE);
        assertEquals(model.getFractalType()== SettingsController.FractalType.TREE,true);

        // Test if an exception has been thrown when trying to set a null fractal with the constructor
        boolean hasThrownAnException = false;
        try{
            Settings modelTest = new Settings(0f,null);
        }catch (NullPointerException e){
            hasThrownAnException = true;
        }
        assertEquals(hasThrownAnException,true);

        // Test if an exception has been thrown when trying to set a null fractal with the method
        hasThrownAnException = false;
        try{
            controller.setFractalType(null);
        }catch (NullPointerException e){
            hasThrownAnException = true;
        }
        assertEquals(hasThrownAnException,true);
    }

    @Test
    public void testCoordinateMapping(){
        // Test default coordinates
        assertEquals(model.getCenterX()==0f,true);
        assertEquals(model.getCenterY()==0f,true);

        // Test mapping methods
        // Basic tests
        assertEquals(model.getMapX(model.getCenterX())==0f,true);
        assertEquals(model.getMapY(model.getCenterY())==0f,true);
        assertEquals(model.getViewY(model.getCenterY())==0f,true);
        assertEquals(model.getViewX(model.getCenterY())==0f,true);

        // Advanced tests
        // Move
        controller.setZoom(2);
        final int DX = 5;
        final int DY = 10;
        controller.move(DX,DY);
        assertEquals(model.getCenterX()==DX,true);
        assertEquals(model.getCenterY()==DY,true);

        // Mapping
        final int X = 5;
        final int Y = 10;
        assertEquals(model.getMapX(X),(X- model.getCenterX())/ model.getZoomLevel(),0f);
        assertEquals(model.getMapY(Y),(Y- model.getCenterY())/ model.getZoomLevel(),0f);
        assertEquals(model.getViewX(X),model.getCenterX()* model.getZoomLevel() + X,0f);
        assertEquals(model.getViewY(Y),model.getCenterY()* model.getZoomLevel() + Y,0f);

    }

    @Test
    public void testIterationsUpdate(){
        // Test min iterations change
        controller.changeIterations(model.getFractalType().getDrawer().getMinIterations()-50);
        assertEquals(model.getIterations()== model.getFractalType().getDrawer().getMinIterations(),true);

        // Test max iterations change
        controller.changeIterations(model.getFractalType().getDrawer().getMaxIterations()+50);
        assertEquals(model.getIterations()== model.getFractalType().getDrawer().getMaxIterations(),true);
    }

    @Test
    public void testColorationMode(){
        // test default is set to ORIGINAL
        assertEquals(model.getColorationMode()==SettingsController.ColorationMode.ORIGINAL,true);

        // Test change coloration mode
        controller.changeColorationMode(SettingsController.ColorationMode.BLUE);
        assertEquals(model.getColorationMode()== SettingsController.ColorationMode.BLUE,true);

        // Test exception on color mode change
        boolean hasThrownAnException = false;
        try{
            controller.changeColorationMode(null);
        }catch (NullPointerException e){
            hasThrownAnException = true;
        }
        assertEquals(hasThrownAnException,true);
    }
}
