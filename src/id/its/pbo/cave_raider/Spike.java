package id.its.pbo.cave_raider;

import java.awt.image.BufferedImage;
public class Spike extends Map {
    public static String TYPE = "s";
    public static BufferedImage TILESHEET;

    private int tick = -1;
    private int imageType;

    public Spike(int xPos, int yPos, String data) {
        super(xPos, yPos, TYPE, data, false, false, COLOR, new String[] { "t" }, TILESHEET);

    }

    public boolean touch(Player p) {
        if (tick == -1) {
            tick = 0;
        }
        return super.touch(p);
    }

    public boolean isIrregular() {
        return isKill();
    }

}
