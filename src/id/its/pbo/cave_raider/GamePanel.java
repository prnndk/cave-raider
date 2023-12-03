package id.its.pbo.cave_raider;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GamePanel(int width, int height){
        this.setPreferredSize(new Dimension(width, height));
    }
}
