package its.pbo.caveRaider.Games;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import its.pbo.utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if(lvlIndex>=levels.size()) {
			lvlIndex =0;
			System.out.println("yahaha udah selesai");
			GameState.state = GameState.MENU;
		}
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getObjectManager().loadObjects(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setMaxLvlOffsetX(newLevel.getLvlOffsetX());
		game.getPlaying().setMaxLvlOffsetY(newLevel.getLvlOffsetY());
		}

	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.getAllLevels();
		for (BufferedImage img : allLevels) {
			levels.add(new Level(img));
		}
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 12; j++) {
				int index = i * 12 + j;
				levelSprite[index] = img.getSubimage(j * 32, i * 32, 32, 32);
			}
		}
	}

	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (int j = 0; j < levels.get(lvlIndex).getLevelData().length; j++)
			for (int i = 0; i < levels.get(lvlIndex).getLevelData()[j].length; i++) {
				int index = levels.get(lvlIndex).getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - xLvlOffset, Game.TILES_SIZE * j - yLvlOffset,
						Game.TILES_SIZE,
						Game.TILES_SIZE, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	public int getAmountOfLevel() {
		return levels.size();
	}
}