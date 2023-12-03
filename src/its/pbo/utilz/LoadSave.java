package its.pbo.utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import its.pbo.caveRaider.Game;

public class LoadSave {
	public static final String PLAYER_ATLAS = "tilesheet2.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_custom.png";
	
	public static BufferedImage GetSpiritAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+fileName);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return img;
	}	
	public static int[][] GetLeveldata(){
		BufferedImage img = GetSpiritAtlas(LEVEL_ONE_DATA);
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
}
