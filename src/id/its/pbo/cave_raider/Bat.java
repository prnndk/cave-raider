package id.its.pbo.cave_raider;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Bat extends Entity {

    public static final String TYPE = "b";
    private static BufferedImage TILESHEET;

    static {
        File batImgPath = new File(Game.IMAGES + TYPE + "/tilesheet.png");
        try {
            TILESHEET = ImageIO.read(batImgPath);
        } catch (Exception e) {
            System.out.println("Image/File of " + batImgPath.getPath() + " not found");
            e.printStackTrace();
        }
    }

    public Bat(double x, double y, String data) {
        super(x, y, 0, 0, TILESHEET);
        double speed = 7.5 / (double) Game.FPS;
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

    public BufferedImage getImage(int scale) {
        BufferedImage sheet = getTileSheet();
        BufferedImage img;
        switch (getDirectionChar()) {
            case "^":
            case ">":
            default:
                img = sheet.getSubimage(getFrame() * Game.IMAGE_SIZE, 0, Game.IMAGE_SIZE, Game.IMAGE_SIZE);
                break;
            case "v":
            case "<":
                img = sheet.getSubimage(getFrame() * Game.IMAGE_SIZE, Game.IMAGE_SIZE, Game.IMAGE_SIZE,Game.IMAGE_SIZE);
                break;
        }
        return Map.transformImage(img, 0, scale / Game.IMAGE_SIZE);
    }

    // public void updateGraphics(Level l) {
    //     updateGraphics(l.getAnimationKey(2, 5));
    // }
    
}
