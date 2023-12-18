package its.pbo.utilz;

import static its.pbo.utilz.Constants.EnemyConstants.BAT;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import its.pbo.caveRaider.Bat;
import its.pbo.caveRaider.Game;

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
}
