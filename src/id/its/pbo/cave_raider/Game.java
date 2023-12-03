package id.its.pbo.cave_raider;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import id.its.pbo.input.KeyboardInput;

public class Game {
    public static final int FPS = 120;
    public static final int IMAGE_SIZE = 32;
    public static final int WIDTH = Camera.DEFAULT_SCALE * 13;
    public static final int HEIGHT = Camera.DEFAULT_SCALE * 26;
    public static final String IMAGES = "Res/image/";
    public static final String LEVELS = "src/id/its/pbo/cave_raider/levels/";
    public static final String FONT = "src/id/its/pbo/cave_raider/font/PressStart2P-Regular.ttf";
    public static final String TITLE = "Cave Raider";

    private Frame frame;
    private Graphics g;
    private int selX = 0;
    private int selY = 0;
    // private Level playingLevel;
    private boolean active = true;
    private int totalCoin;

    private Player player;

    public Game() {
        player = new Player(50, 50);
        DrawingPanel panel = new DrawingPanel(WIDTH, HEIGHT);
        g = panel.getGraphics();
        panel.addKeyListener(new KeyboardInput(this));

        Timer timer = new Timer(1000 / FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        timer.start();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game();
            }
        });
    }

    public void play() {
    }

    public void update() {
        player.tryMove();
        player.move();
    }

    public void draw() {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(player.getImage(1), player.getX(), player.getY(), null);
        g.dispose();
    }

    public Player getPlayer() {
        return player;
    }
}
