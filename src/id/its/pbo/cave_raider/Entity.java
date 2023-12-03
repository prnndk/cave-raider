package id.its.pbo.cave_raider;

import java.awt.image.BufferedImage;

public class Entity {
	private BufferedImage tileSheet;
	private boolean alive = true;
	private String type;
	private double xPos;
	private double yPos;
	private double xDir;
	private double yDir;
	private int frame;
	private int wait = 0;

	public Entity(double xPos, double yPos, double xDir, double yDir, BufferedImage tileSheet) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xDir = xDir;
		this.yDir = yDir;
		this.tileSheet = tileSheet;
	}

	public void setDirection(double x, double y) {
		this.xDir = x;
		this.yDir = y;
	}

	public BufferedImage getTileSheet() {
		return tileSheet;
	}

	public void setTileSheet(BufferedImage tileSheet) {
		this.tileSheet = tileSheet;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getX() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getY() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	public double getxDir() {
		return xDir;
	}

	public double getyDir() {
		return yDir;
	}

	public int getFrame() {
		return frame;
	}

	public BufferedImage getImage(int scale) {
		return null;
	}

	public static Entity makeEntity(String type, double x, double y) {
		Entity e = null;
		return e;
	}

	public void updateGraphics(int frame) {
		this.frame = frame;
	}

	public void touch() {
		xDir *= -1;
		yDir *= -1;
		// wait = Game.FPS;
	}

	public int[] tryMove() {
		double newX = xPos + xDir;
		double newY = yPos + yDir;

		if (Math.signum(xDir) > 0)
			newX = Math.ceil(newX);
		if (Math.signum(yDir) > 0)
			newY = Math.ceil(newY);
		int[] result = { (int) newX, (int) newY };
		return result;
	}

	public boolean isMoving() {
		return xDir == 0 && yDir == 0;
	}

	public String getDirectionChar() {
		if (xDir > 0)
			return ">";
		if (xDir < 0)
			return "<";
		if (yDir > 0)
			return "v";
		if (yDir < 0)
			return "^";
		return ">";
	}

	public void update(Player p) {
		if (wait <= 0) {
			xPos = round(xPos + xDir, 5);
			yPos = round(yPos + yDir, 5);
		} else {
			wait--;
		}
		if ((Math.floor(xPos) == p.getX() || Math.ceil(xPos) == p.getX())
				&& (Math.floor(yPos) == p.getY() || Math.ceil(yPos) == p.getY())) {
			p.setAlive(false);
		}
	}

	public double round(double value, int decimalPlaces) {
		return (int) (value * Math.pow(10, decimalPlaces) + 0.5) / (double) Math.pow(10, decimalPlaces);
	}
}
