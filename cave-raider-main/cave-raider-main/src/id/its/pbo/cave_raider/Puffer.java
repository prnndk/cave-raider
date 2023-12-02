import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Puffer extends Entity {
    public static final String TYPE = "f";
    private static BufferedImage TILESHEET;

    static {
        File imgPath = new File(Game.IMAGES + TYPE + "/tilesheet.png");
        try {
            TILESHEET = ImageIO.read(imgPath);
        } catch (IOException e) {
            System.out.println("File '" + imgPath.getPath() + "' not found");
            e.printStackTrace();
        }
    }

    private int tick = 0;
    private int[] frame = new int[] { 0, 0 };

    public Puffer(double x, double y, String data) {
        super(x, y, 0, 0, TILESHEET);
    }

    public void update(Player p) {
        if (tick >= Game.TPS * 1.5) {
            if (getX() - 1 <= p.getX() && getX() + 1 >= p.getX() && getY() - 1 <= p.getY() && getY() + 1 >= p.getY()) {
                p.setAlive(false);
            }
        }
        tick = (tick + 1) % (int) (Game.TPS * 2.25);
    }

    public BufferedImage getImage(int scale) {
        BufferedImage sheet = getTileSheet();
        int size = 3 * Game.IMAGE_SIZE;
        return Cell.transformImage(sheet.getSubimage(frame[1] * size, frame[0] * size, size, size), 0, scale / Game.IMAGE_SIZE);
    }

    public void updateGraphics(Level l) {
        if (tick < Game.TPS / 8 && l.getAnimationTick() > 2) {
            frame[0] = 1;
            frame[1] = 1;
        } else if (tick < 1.5 * Game.TPS / 8 && l.getAnimationTick() > 2) {
            frame[0] = 1;
            frame[1] = 0;
        } else if (tick < 1.5 * Game.TPS - 2 * Game.TPS / 8) {
            frame[0] = 0;
            frame[1] = l.getAnimationKey(1, 4);
        } else if (tick < 1.5 * Game.TPS - Game.TPS / 8) {
            frame[0] = 1;
            frame[1] = 0;
        } else if (tick < 1.5 * Game.TPS) {
            frame[0] = 1;
            frame[1] = 1;
        } else if (tick < 1.5 * Game.TPS + Game.TPS / 8) {
            frame[0] = 1;
            frame[1] = 2;
        } else if (tick < 2.25 * Game.TPS - Game.TPS / 8) {
            frame[0] = 2;
            frame[1] = l.getAnimationKey(1, 2);
        } else {
            frame[0] = 1;
            frame[1] = 2;
        }
    }

    public boolean isBig() {
        return tick >= Game.TPS * 2;
    }
}