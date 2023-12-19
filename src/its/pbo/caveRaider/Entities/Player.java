package its.pbo.caveRaider.Entities;

import static its.pbo.utilz.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import its.pbo.caveRaider.Games.Game;
import its.pbo.caveRaider.Games.Playing;
import its.pbo.utilz.LoadSave;

import static its.pbo.utilz.Constants.PlayerConstants.*;
import static its.pbo.utilz.HelpMethods.canMoveHere;

public class Player extends Entity {
	private BufferedImage[][] image;
	public static final String TYPE = "p";
	private int animTick, animIndex, animSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false,alive = true;
	private boolean left, up, right, down, canMove, isMoving;
	private double playerSpeed = 15f, xSpeed = 0, ySpeed = 0;
	private int[][] lvlData;
	private float xDrawOffset = 2 * Game.SCALE;
	private float yDrawOffset = 7 * Game.SCALE;
	private int tileY = 0;

	private int flipX = 0;
	private int flipY = 0;
	private int flipW = 1;
	private int flipH = 1;
	
	private Playing playing;
	public Player(double x, double y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadImage();
		initHitBox(x, y, 13 * Game.SCALE, 11 * Game.SCALE);
	}
	
	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitBox.x = x;
		hitBox.y = y;
	}

	public void update() {
		if(!alive) {
			playing.setGameOver(true);
			return;
		}
		updatePos();
		updateAnimationTick();
		setAnimation();

	}

	private void checkSpikesTouched() {
		playing.checkSpikesTouched(this);

	}

	private void checkPotionTouched() {
		playing.checkPotionTouched(hitBox);
	}


	public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
		g.drawImage(image[playerAction][animIndex], (int) (hitBox.x - xDrawOffset) - xLvlOffset + flipX,
				(int) (hitBox.y - yDrawOffset) - yLvlOffset +flipY,
				width*flipW, height *flipH, null);
	}

	private void updateAnimationTick() {
		animTick++;
		if (animTick >= animSpeed) {
			animTick = 0;
			animIndex++;
			if (animIndex >= GetSpriteAmount(playerAction)) {
				animIndex = 0;
			}
		}
	}

	

	private void setAnimation() {
		int startAnim = playerAction;
		if (isMoving && moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (startAnim != playerAction) {
			resetAniTick();
		}
	}

	private void resetAniTick() {
		animTick = 0;
		animIndex = 0;
	}

	private void updatePos() {
		moving = false;
		isMoving = false;
		if (!left && !right && !up && !down) {
			return;
		}
		xSpeed = 0;
		ySpeed = 0;
		if(left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if(right) {
			xSpeed = playerSpeed;
			flipX = 0;
			flipW = 1;
		}
		if(up) {
			ySpeed -= playerSpeed;
			flipY = height;
			flipH = -1;
		}
		if(down) {
			ySpeed = playerSpeed;
			flipY = 0;
			flipH = 1;
		}
		if (canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData)) {
			Thread movementThread = new Thread(() -> {
				while (canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData)) {
					hitBox.x += xSpeed;
					hitBox.y += ySpeed;
					checkPotionTouched();
					checkSpikesTouched();
					tileY = (int) (hitBox.y / Game.TILES_SIZE);
					if (canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData)) {
						moving = true;
						isMoving = true;
					} else {
						moving = false;
						isMoving = false;
					}
					setAnimation();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			movementThread.start();
		}
		setAnimation();
		
	}

	
	private void loadImage() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		image = new BufferedImage[2][6];
		for (int j = 0; j < image.length; j++)
			for (int i = 0; i < image[j].length; i++)
				image[j][i] = img.getSubimage(i * 12, j * 12, 12, 12);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void resetAll() {
		resetDirBooleans();
		moving = false;
		isMoving = false;
		playerAction = IDLE;
		flipH = 1;
		flipW = 1;
		hitBox.x = x;
		hitBox.y = y;
		
	}

	public int getTileY() {
		return tileY;
	}

	public boolean isMoving() {
		return isMoving;
	}
}
