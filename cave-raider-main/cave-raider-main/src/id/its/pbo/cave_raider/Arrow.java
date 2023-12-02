import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class Arrow extends Entity {
    public static final String TYPE = "l";
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

    public Arrow(double x, double y, String data) {
        super(x, y, 0, 0, TILESHEET);
        double speed = 15 / (double) Game.TPS;
        switch (data) {
            case "^":
                setDirection(0, -speed);
                break;
            case ">":
                setDirection(speed, 0);
                break;
            case "v":
                setDirection(0, speed);
                break;
            case "<":
                setDirection(-speed, 0);
                break;
        }
    }

    public void touch() {
        setAlive(false);
    }

    public BufferedImage getImage(int scale) {
        int x = 0;
        switch (getDirChar()) {
            case ">":
                x = 0;
                break;
            case "v":
                x = 1;
                break;
            case "<":
                x = 2;
                break;
            case "^":
                x = 3;
                break;
        }
        return Cell.transformImage(getTileSheet().getSubimage(x * Game.IMAGE_SIZE, 0, Game.IMAGE_SIZE, Game.IMAGE_SIZE), 0, scale / Game.IMAGE_SIZE);
    }
}