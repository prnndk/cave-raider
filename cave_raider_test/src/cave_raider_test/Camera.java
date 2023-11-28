package cave_raider_test;

public class Camera {
    public static final int DEFAULT_SCALE = 36;
    public static final double EASING_RATE = 7 / (double) Game.TPS;

    private int scale = DEFAULT_SCALE;
    private Player player;

    private int minX = Integer.MIN_VALUE;
    private int minY = Integer.MIN_VALUE;
    private int maxX = Integer.MAX_VALUE;
    private int maxY = Integer.MAX_VALUE;

    public Camera(Player player) {
        this.player = player;
        updatePosition();
    }

    public int getScale() {
        return scale;
    }

    public int getX() {
        return (int) (player.getX() * scale + (scale - Game.WIDTH) / 2);
    }

    public int getY() {
        return (int) (player.getY() * scale + (scale - Game.HEIGHT) / 2);
    }

    public void move() {
        int targetX = (int) (player.getX() * scale + (scale - Game.WIDTH) / 2);
        int targetY = (int) (player.getY() * scale + (scale - Game.HEIGHT) / 2);
        int deltaX = targetX - getX();
        int deltaY = targetY - getY();
        deltaX = (int) (deltaX * EASING_RATE);
        deltaY = (int) (deltaY * EASING_RATE);
        setPos(getX() + deltaX, getY() + deltaY);
    }

    private void updatePosition() {
        setPos(getX(), getY());
    }

    public void setPos(int x, int y) {
        int clampedX = Math.min(Math.max(x, minX), maxX);
        int clampedY = Math.min(Math.max(y, minY), maxY);

        int playerX = (int) ((clampedX - (scale - Game.WIDTH) / 2) / (double) scale);
        int playerY = (int) ((clampedY - (scale - Game.HEIGHT) / 2) / (double) scale);

        player.setX(playerX);
        player.setY(playerY);
    }

    public boolean isVisible(double gridX, double gridY) {
        return (gridX + 1) * scale > getX() &&
                (gridY + 1) * scale > getY() &&
                gridX * scale < getX() + Game.WIDTH &&
                gridY * scale < getY() + Game.HEIGHT;
    }

    public void setMinX(int value) {
        minX = value;
    }

    public void setMaxX(int value) {
        maxX = value;
    }

    public void setScale(int value) {
        scale = value;
        updatePosition();
    }

    public void offsetY(int value) {
        setPos(getX(), getY() + value);
    }
}

