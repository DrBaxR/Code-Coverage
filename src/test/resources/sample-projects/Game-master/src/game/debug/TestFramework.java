package game.debug;

import game.framework.Game;
import game.framework.GameTime;
import game.input.Keyboard;
import game.input.Mouse;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestFramework extends Game
{
    /**
     * Any objects/variables that need to be Initialized should do so
     * in Initialize().
     */
    @Override
    public void initialize()
    {
        // Initialize stuff in Base
        super.initialize();
        // TODO:
    }

    /**
     * Any Content that needs to be loaded should do so
     * in loadContent().
     */
    @Override
    public void loadContent()
    {
        // All Resources should be placed in the resource folder
        // A "/resource/" is appended to the path.
        // To load an image; Texture2D texture = new Texture2D("path");
        // This file should load on all OS inside the jar :)
        // TODO:
    }

    /**
     * Any Content that needs to be unloaded should do so
     * in unloadContent().
     * Note: I wouldn't worry about this; Its not implemented properly as of now..
     */
    @Override
    public void unloadContent()
    {
        // TODO:
    }

    /**
     * Objects/Variables/Logic/Input that need to be updated should be placed
     * in this method.
     * @param gameTime
     */
    @Override
    public void update(GameTime gameTime)
    {
        // Call base class
        super.update(gameTime);
        // TODO:
        this.fps.update(gameTime);


        //<editor-fold defaultstate="collapsed" desc="System & Menu Keys">
        if(Keyboard.keyDownOnce(KeyEvent.VK_ESCAPE))
        {
            Game.exitGame();
        }
        //</editor-fold>
    }

    /**
     * This method draws images to the screen buffer
     * @param g2d
     */
    @Override
    public void draw(Graphics2D g2d)
    {
        // Call Base Class to clear Screen
        super.draw(g2d);
        // TODO:
        this.fps.draw(g2d);
        g2d.drawString("Mouse: (" + Mouse.getPosition().x + "," + Mouse.getPosition().y + ")", 10, 60);
    }

    /**
     * Main Entrance into TestFramework
     * @param args
     */
    public static void main(String[] args)
    {
        TestFramework test = new TestFramework();
        test.setTitle("Java Game Framework - Alpha");
        test.setDimensions(800, 600);
        test.run();
    }
}
