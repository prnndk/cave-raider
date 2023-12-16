package its.pbo.caveRaider;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import static its.pbo.utilz.Constants.EnemyConstants.*;

import its.pbo.utilz.LoadSave;

public class EnemyManager {
	private Playing playing;
	private BufferedImage[][] batArray;
	private ArrayList<Bat> bats = new ArrayList<Bat>();
	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImages();
		addEnemies();
	}
	private void addEnemies() {
		// TODO Auto-generated method stub
		bats = LoadSave.getBats();	
	}
	public void update(int[][] lvlData) {
		for(Bat b: bats) {
			b.update(lvlData);
		}
	}
	
	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		drawBat(g,xLvlOffset,yLvlOffset);
	}
	private void drawBat(Graphics g,int xLvlOffset, int yLvlOffset) {
		for(Bat b: bats) {
			g.drawImage(batArray[b.getEnemyState()][b.getAnimationIndex()],(int) b.getHitBox().x - xLvlOffset,(int) b.getHitBox().y - yLvlOffset, (int)(BAT_WIDTH*1.5),(int) (BAT_HEIGHT*1.5),null);
		}
	}
	private void loadEnemyImages() {
		batArray = new BufferedImage[2][5];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.BAT_SPRITE);
		for (int j = 0; j < batArray.length; j++) {
			for (int i = 0; i < batArray[j].length; i++) {
				batArray[j][i] = temp.getSubimage(i*BAT_WIDTH_DEFAULT, j*BAT_HEIGHT_DEFAULT,BAT_WIDTH_DEFAULT , BAT_HEIGHT_DEFAULT);
			}
		}
	}

	public ArrayList<Bat> getBats() {
        return bats;}
}
