package id.its.pbo.cave_raider;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;

public class Game extends MouseAdapter implements KeyListener {
    public static final int FPS = 120;
    public static final int IMAGE_SIZE = 32;
    public static final String IMAGES = "src/id/its/pbo/cave_raider/images/";
    public static final String LEVELS = "src/id/its/pbo/cave_raider/levels/";
    public static final String FONT = "src/id/its/pbo/cave_raider/font/PressStart2P-Regular.ttf";
    public static final String TITLE = "Cave Raider";
    public static final String VERSION = "v1.0.0";
    public static final String AUTHOR = "Created by: ";

    private DrawingPanel frame;
    private Graphics g;
    private int selX = 0;
    private int selY = 0;
    // private Level playingLevel;
    private boolean active = true;
    private int totalCoin;

    public Game() {
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }
}
