package cave_raider_test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class GameFrame extends JFrame {
    private Game game;

    public GameFrame() {
        game = new Game();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setTitle("Move the Player");

        add(game);

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.getPlayer().setDirection(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });

        setFocusable(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame gameFrame = new GameFrame();
                gameFrame.setVisible(true);
            }
        });
    }
}
