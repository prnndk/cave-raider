	package its.pbo.utilz;

import static its.pbo.utilz.Constants.EnemyConstants.BAT;
import static its.pbo.utilz.Constants.ObjectConstanst.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import its.pbo.caveRaider.Entities.Bat;
import its.pbo.caveRaider.Games.Game;
import its.pbo.caveRaider.Objects.Cannon;
import its.pbo.caveRaider.Objects.Dots;
import its.pbo.caveRaider.Objects.Projectile;
import its.pbo.caveRaider.Objects.Spike;


public class HelpMethods {
	public static boolean canMoveHere(double x, double y, double width, double height, int[][] lvlData) {
		if (!isSolid(x, y, lvlData))
			if (!isSolid(x + width, y + height, lvlData))
				if (!isSolid(x + width, y, lvlData))
					if (!isSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean isSolid(double x, double y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		int maxHeight = lvlData.length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;

		double xIndex = x / Game.TILES_SIZE;
		double yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];
		return isTileSolid((int)xIndex,(int)yIndex,lvlData);
	}
	
	private static boolean isTileSolid(int xTile,int yTile,int[][] lvlData) {
		int value = lvlData[yTile][xTile];
		if(value>= 48||value<0||value !=11) {
			return true;
		}
		return false;
	}

	public static boolean IsProjectileHittingLevel(Projectile p, int[][] lvlData) {
		return isSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, lvlData);

	}

	public static boolean IsEntityOnFloor(Rectangle2D.Double hitbox, int[][] lvlData) {
		// Check the pixel below bottomleft and bottomright
		if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;

		return true;

	}
	public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
	public static ArrayList<Bat> GetBats(BufferedImage img){
		ArrayList<Bat> list = new ArrayList<Bat>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == BAT) {
					list.add(new Bat(i * Game.TILES_SIZE,j * Game.TILES_SIZE));
				}
			}
		return list;
}
	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100) {
					return new Point(i*Game.TILES_SIZE, j*Game.TILES_SIZE);
				}
			}
		return new Point(1*Game.TILES_SIZE, 1*Game.TILES_SIZE);
	}
	
	public static Point GetGoal(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 150) {
					return new Point(i*Game.TILES_SIZE, j*Game.TILES_SIZE);
				}
			}
		return new Point(1*Game.TILES_SIZE, 1*Game.TILES_SIZE);
	}

	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value = lvlData[yTile][xTile];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}

	public static boolean CanCannonSeePlayer(int[][] lvlData, Rectangle2D.Double firstHitbox, Rectangle2D.Double secondHitbox, int yTile) {
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

		if (firstXTile > secondXTile)
			return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
		else
			return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
	}

	public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
		for (int i = 0; i < xEnd - xStart; i++)
			if (IsTileSolid(xStart + i, y, lvlData))
				return false;
		return true;
	}

	public static ArrayList<Cannon> GetCannons(BufferedImage img) {
		ArrayList<Cannon> list = new ArrayList<>();

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == CANNON_LEFT || value == CANNON_RIGHT)
					list.add(new Cannon(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
			}

		return list;
	}

	public static ArrayList<Dots> GetPotions(BufferedImage img) {
		ArrayList<Dots> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == COIN || value == DOT) {
					list.add(new Dots(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));					
				}
			}

		return list;
	}

	public static ArrayList<Spike> GetSpikes(BufferedImage img) {
		ArrayList<Spike> list = new ArrayList<>();

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == SPIKE)
					list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SPIKE));
			}

		return list;
	}
}
