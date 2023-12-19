package its.pbo.caveRaider.Games;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import its.pbo.caveRaider.Entities.Bat;
import its.pbo.caveRaider.Objects.Cannon;
import its.pbo.caveRaider.Objects.Dots;
import its.pbo.caveRaider.Objects.Spike;
import its.pbo.utilz.HelpMethods;
import its.pbo.utilz.LoadSave;
import static its.pbo.utilz.HelpMethods.GetLevelData;
import static its.pbo.utilz.HelpMethods.GetBats;
import static its.pbo.utilz.HelpMethods.GetPlayerSpawn;
import static its.pbo.utilz.HelpMethods.GetGoal;
import static its.pbo.utilz.HelpMethods.GetPotions;
import static its.pbo.utilz.HelpMethods.GetSpikes;
import static its.pbo.utilz.HelpMethods.GetCannons;

public class Level {
	private BufferedImage img;
	private int[][] levelData;
	private ArrayList<Bat> bats;
	private ArrayList<Dots> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private int lvlTilesWide;
    private int lvlTilesHigh;
    private int maxTilesOffsetX;
    private int maxTilesOffsetY;
    private int maxLvlOffsetX;
    private int maxLvlOffsetY;
    private Point playerSpawn, goalSpawn;
    
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffset();
		calcPlayerSpawn();
		calcGoalSpawn();
		createPotions();
		createSpikes();
		createCannons();
	}

	private void createCannons() {
		cannons = HelpMethods.GetCannons(img);
	}

	private void createSpikes() {
		spikes = HelpMethods.GetSpikes(img);
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
		
	}

	private void createPotions() {
		potions = HelpMethods.GetPotions(img);
	}

	private void calcGoalSpawn() {
		goalSpawn = GetGoal(img);
	}
	private void calcLvlOffset() {
		lvlTilesWide = img.getWidth();
		lvlTilesHigh = img.getHeight();
		maxTilesOffsetX = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxTilesOffsetY = lvlTilesHigh - Game.TILES_IN_HEIGHT;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffsetX;
		maxLvlOffsetY = Game.TILES_SIZE * maxTilesOffsetY;
	}
	private void createEnemies() {
		bats = GetBats(img);
	}
	private void createLevelData() {
		levelData = GetLevelData(img);
	}
	public int getSpriteIndex(int x,int y) {
		return levelData[y][x];
	}
	
	public int[][] getLevelData(){
		return levelData;
	}
	
	public int getLvlOffsetX() {
		return maxLvlOffsetX;
	}
	public int getLvlOffsetY() {
		return maxLvlOffsetY;
	}
	public ArrayList<Bat> getBats(){
		return bats;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	public Point getGoalSpawn() {
		return goalSpawn; 
	}

	public ArrayList<Dots> getPotions() {
		return potions;
	}

	public ArrayList<Spike> getSpikes() {
		return spikes;
	}
	
	public ArrayList<Cannon> getCannons(){
		return cannons;
	}
	
}
