package id.its.pbo.cave_raider;

import java.awt.image.BufferedImage;

public class Wall extends Map {
    public static String TYPE = "w";
    public static BufferedImage TILESHEET;

    public Wall(int xPos, int yPos, String data) {
        super(xPos, yPos, TYPE, data, true, false, COLOR, new String[] {"w","t","k"}, TILESHEET);
    }
}
