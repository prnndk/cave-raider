package id.its.pbo.cave_raider;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Map {
    public static final Color COLOR = Color.BLACK;
    public static final String TYPE = "_";

    private static BufferedImage TILESHEET;
    private static BufferedImage VALUESHEET;

    static {

    }

    private BufferedImage tileSheet;
    private boolean kill;
    private boolean solid;
    private boolean needsColorRedraw = true;
    private boolean needsTextureRedraw = true;
    private boolean needsDotRedraw = true;
    private String type;
    private String imgCode;
    private int x;
    private int y;
    private int value;
    private int valueTick;
    private Color mapColor;
    private String[] neighbors;

    public Map() {
        this(0, 0, "0");
    }

    public Map(int xPos, int yPos, String data) {
        this(xPos, yPos, "_", data, false, false, COLOR, new String[] {}, TILESHEET);
    }

    public Map(int xPos, int yPos, String type, String data, boolean isSolid, boolean isKill, Color c, String[] ns,
            BufferedImage tileSheet) {
        x = xPos;
        y = yPos;
        if (data.length() > 0) {
            value = Integer.parseInt(data);
            if (value == 1) {
                if (Math.random() < 0.05)
                    value = 2;
                else
                    value = 1;
            }
        } else {
            value = 0;
        }
        this.kill = isKill;
        this.solid = isSolid;
        this.type = type;
        this.mapColor = c;
        this.neighbors = ns;
        this.tileSheet = tileSheet;
    }

    // public static Map newMap(String type, int xPos, int yPos) {
    // switch (type) {
    // case "w":
    // return new map();
    // break;

    // default:
    // break;
    // }
    // }

    public static BufferedImage transformImage(BufferedImage image, double rotation, double scale) {
        return transformImage(image, rotation, scale, false);
    }

    public static BufferedImage transformImage(BufferedImage image, double rotation, double scale, boolean flip) {
        double sin = Math.abs(Math.sin(rotation));
        double cos = Math.abs(Math.cos(rotation));
        int w = image.getWidth();
        int h = image.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = new BufferedImage((int) (newWidth * scale), (int) (newHeight * scale), image.getType());
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        at.rotate(rotation, w / 2, h / 2);

        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        op.filter(image, result);
        return result;
    }

    public boolean touch(Player p) {
        if (value > 0) {
            p.addValue(value);
            value = 0;
            needsDotRedraw = true;
        }
        if (kill)
            p.setAlive(false);

        return solid;
    }

    public boolean needsColorRedraw() {
        if (needsColorRedraw) {
            needsColorRedraw = false;
            return true;
        }
        return false;
    }

    public boolean needsTextureRedraw() {
        if (needsTextureRedraw) {
            needsTextureRedraw = false;
            return true;
        }
        return false;
    }

    public boolean needsDotRedraw() {
        if (needsDotRedraw) {
            needsDotRedraw = false;
            return true;
        }
        return false;
    }

    public void setNeedsColorRedraw(boolean needsColorRedraw) {
        this.needsColorRedraw = needsColorRedraw;
    }

    public void setNeedsTextureRedraw(boolean needsTextureRedraw) {
        this.needsTextureRedraw = needsTextureRedraw;
    }

    public void setNeedsDotRedraw(boolean needsDotRedraw) {
        this.needsDotRedraw = needsDotRedraw;
    }

    public Color getColor() {
        return mapColor;
    }

}
