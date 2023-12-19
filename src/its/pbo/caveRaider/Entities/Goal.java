package its.pbo.caveRaider.Entities;

import static its.pbo.utilz.Constants.EnemyConstants.BAT_HEIGHT;
import static its.pbo.utilz.Constants.EnemyConstants.BAT_WIDTH;
import static its.pbo.utilz.Constants.EnemyConstants.getSpriteAmount;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import its.pbo.utilz.LoadSave;

public class Goal extends Entity {
	
	private BufferedImage[] image;
	private int animationTick;
	private int animationSpeed=25;
	private int animationIndex;
	

	public Goal(double x, double y, int width, int height) {
		super(x, y, width, height);
		loadImage();
		initHitBox(x,y,width, height);
		
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.getX();
		this.y = spawn.getY();
		hitBox.x = x;
		hitBox.y = y;
	}
	
	private void loadImage() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.GOAL_IMG);
		image = new BufferedImage[4];
		for (int j = 0; j < 4; j++) {
			image[j] = img.getSubimage(0, j*12, 12,12);			
		}
	}
	
	public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
		g.setColor(Color.YELLOW);
		g.fillRect((int) hitBox.x - xLvlOffset, (int) hitBox.y - yLvlOffset, width, height);
		g.drawImage(image[animationIndex],(int) hitBox.x - xLvlOffset,(int) hitBox.y - yLvlOffset, width,height,null);
		drawHitBox(g, xLvlOffset, yLvlOffset);
	}
	
	private void updateAnimationTick() {
		animationTick++;
		if(animationTick>=animationSpeed) {
			animationTick=0;
			animationIndex++;
			if(animationIndex >= 4) {
				animationIndex =0;
			}
		}
	}
	
	public boolean touchPlayer(Player p) {
		return this.getHitBox().intersects(p.getHitBox());
	}

	public void update() {
		updateAnimationTick();
	}

}
