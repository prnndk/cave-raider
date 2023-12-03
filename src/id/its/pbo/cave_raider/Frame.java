package id.its.pbo.cave_raider;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class Frame extends JFrame {

    private ArrayList<BufferedImage> layers;
    // private Level level;

    public Frame() {
        super(Game.TITLE);
        // level = l;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // setVisible(true);

        // layers = new ArrayList<>(4);
        // for (int i = 0; i < 4; i++) {
        // layers.add(new BufferedImage(level.getMap().get(0).size() *
        // level.getCamera().getScale(),
        // level.getMap().size() * level.getCamera().getScale(),
        // BufferedImage.TYPE_INT_ARGB));
        // }
    }

    public static BufferedImage rotateImage(BufferedImage img, double rotation) {
        double sin = Math.abs(Math.sin(rotation));
        double cos = Math.abs(Math.cos(rotation));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage output = new BufferedImage(newWidth, newHeight, img.getType());
        AffineTransform at = new AffineTransform();
        at.rotate(rotation, w / 2, h / 2);

        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        op.filter(img, output);
        return output;
    }

    // public void updateColorLayer() {
    // BufferedImage layer = layers.get(0);
    // Graphics g = layer.getGraphics();
    // int scale = level.getCamera().getScale();
    // for (ArrayList<Map> row : level.getMap()) {
    // for (Map c : row) {
    // if (c.needsColorRedraw()) {
    // int x = c.getX() * scale;
    // int y = c.getY() * scale;
    // g.setColor(c.getColor());
    // g.fillRect(x, y, scale, scale);
    // }
    // }
    // }
    // g.dispose();
    // }

    // public void updateTileLayer() {
    // BufferedImage layer = layers.get(1);
    // Graphics g = layer.getGraphics();
    // int scale = level.getCamera().getScale();
    // for (ArrayList<Map> row : level.getMap()) {
    // for (Map c : row) {
    // if (c.needsTextureRedraw()) {
    // int x = c.getX() * scale;
    // int y = c.getY() * scale;
    // BufferedImage tile = c.getImage(scale);
    // ((Graphics2D)
    // g).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
    // g.fillRect(x, y, scale, scale);
    // ((Graphics2D)
    // g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    // g.drawImage(rotateImage(tile, -c.getRotation() * Math.PI / 2), x, y, null);
    // }
    // }
    // }
    // g.dispose();
    // }

    // public void updateDotLayer() {
    // BufferedImage layer = layers.get(2);
    // Graphics g = layer.getGraphics();
    // int scale = level.getCamera().getScale();
    // for (ArrayList<Map> row : level.getMap()) {
    // for (Map c : row) {
    // if (c.needsDotRedraw()) {
    // int x = c.getX() * scale;
    // int y = c.getY() * scale;
    // BufferedImage tile = c.getDot(scale);
    // ((Graphics2D)
    // g).setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
    // g.fillRect(x, y, scale, scale);
    // ((Graphics2D)
    // g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    // if (tile != null) {
    // g.drawImage(tile, x, y, null);
    // }
    // }
    // }
    // }
    // g.dispose();
    // }

    // public void updateEntityLayer() {
    // BufferedImage layer = layers.get(3);
    // layer = new BufferedImage(layer.getWidth(), layer.getHeight(),
    // BufferedImage.TYPE_INT_ARGB);
    // layers.set(3, layer);

    // Graphics g = layer.getGraphics();
    // int scale = level.getCamera().getScale();
    // for (Entity e : level.getEntities()) {
    // if (e.getType().equals("f") || level.getCamera().isVisible(e.getX(),
    // e.getY())) {
    // e.updateGraphics(level);
    // int x = (int) (e.getX() * scale);
    // int y = (int) (e.getY() * scale);
    // if (e.getType().equals("f")) {
    // x -= scale;
    // y -= scale;
    // }
    // g.drawImage(e.getImage(scale), x, y, null);
    // }
    // }
    // Player p = level.getPlayer();
    // int x = (int) (p.getX() * scale);
    // int y = (int) (p.getY() * scale);
    // g.drawImage(p.getImage(scale), x, y, null);

    // g.dispose();
    // }

    // public BufferedImage buildFrame() {
    // // update everything
    // TimeTracker.start("color layer");
    // updateColorLayer();
    // TimeTracker.start("tile layer");
    // updateTileLayer();
    // TimeTracker.start("dot layer");
    // updateDotLayer();
    // TimeTracker.start("entity layer");
    // updateEntityLayer();

    // TimeTracker.start("coordinates");

    // // coordinate numbers
    // Camera c = level.getCamera();
    // int sx1 = Math.max((int) (c.getX()), 0);
    // int dx1 = -(int) (c.getX()) + sx1;
    // int w = Game.WIDTH - Math.max(0, (Game.WIDTH + sx1) -
    // layers.get(0).getWidth());
    // int sx2 = sx1 + w;
    // int dx2 = dx1 + w;

    // int sy1 = Math.max((int) (c.getY()), 0);
    // int dy1 = -(int) (c.getY()) + sy1;
    // int h = Game.HEIGHT - Math.max(0, (Game.HEIGHT + sy1) -
    // layers.get(0).getHeight());
    // int sy2 = sy1 + h;
    // int dy2 = dy1 + h;

    // TimeTracker.start("draw to frame");

    // // draw to frame
    // Graphics g = getGraphics();
    // g.setColor(Map.COLOR);
    // g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
    // for (BufferedImage layer : layers) {
    // g.drawImage(layer, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    // }
    // g.dispose();

    // TimeTracker.start("return frame");
    // return frame;
    // }

    // public void addMouseListener(Game game) {
    // super.addMouseListener(game);
    // }
}