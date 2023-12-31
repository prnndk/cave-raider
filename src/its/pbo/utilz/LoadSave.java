package its.pbo.utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import its.pbo.caveRaider.Entities.Bat;
import its.pbo.caveRaider.Games.Game;

import static its.pbo.utilz.Constants.EnemyConstants.BAT;

public class LoadSave {

	public static final String PLAYER_ATLAS = "tilesheet2.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_custom.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String BAT_SPRITE = "bat.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	public static final String GOAL_IMG = "goal.png";
	public static final String CANNON_ATLAS = "cannon_atlas.png";
	public static final String CANNON_BALL = "ball.png";
	public static final String POTION_ATLAS = "potions_sprites.png";
	public static final String TRAP_ATLAS = "trap_atlas.png";
	public static final String DOTS_ATLAS = "dots.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static BufferedImage[] getAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file  = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		
		for (int i=0;i<filesSorted.length;i++) {
			for (int j = 0; j < files.length; j++) {
				if(files[j].getName().equals(""+ (i+ 1) + ".png")) {
					filesSorted[i] = files[j];
				}
			}
		}
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		
		for (int i = 0; i < imgs.length; i++) {
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return imgs;
	}
	
	
	
}