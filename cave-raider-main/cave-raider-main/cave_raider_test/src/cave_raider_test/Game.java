package cave_raider_test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Game extends JFrame {
    public static final int TPS = 60;
    public static final int IMAGE_SIZE = 40;

    private Player player;

    public Game() {
        player = new Player(50, 50);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setTitle("Player Test");

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                getPlayer().setDirection(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        Timer timer = new Timer(1000 / TPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPlayer().attemptMove();
                getPlayer().move();
                repaint();
            }
        });

        timer.start();
    }

    // Method to get the player instance
    public Player getPlayer() {
        return player;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        int scale = IMAGE_SIZE * 2;
        BufferedImage playerImage = getPlayer().getImage(scale);

        g2d.drawImage(playerImage, (int) getPlayer().getX(), (int) getPlayer().getY(), this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game().setVisible(true);
            }
        });
    }
}
