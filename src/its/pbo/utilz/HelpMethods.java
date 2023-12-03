package its.pbo.utilz;

import its.pbo.caveRaider.Game;

public class HelpMethods {
	public static boolean canMoveHere(double x, double y, double width,double height,int[][] lvlData ) {
		if (!isSolid(x, y, lvlData))
			if (!isSolid(x + width, y + height, lvlData))
				if (!isSolid(x + width, y, lvlData))
					if (!isSolid(x, y + height, lvlData))
						return true;
		return false;
	}
	
	private static boolean isSolid(double x, double y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if(x<0||x>=maxWidth)
			return true;
		if(y<0||y>=Game.GAME_HEIGHT)
			return true;
		
		double xIndex = x/Game.TILES_SIZE;
		double yIndex = y/Game.TILES_SIZE;
		
		int value = lvlData[(int)yIndex][(int) xIndex];
		if(value>=48 || value < 0 ||value!=11)
			return true;
		return false;
	}
}
