package its.pbo.caveRaider;

import static its.pbo.utilz.Constants.PlayerConstants.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import its.pbo.utilz.LoadSave;

import static its.pbo.utilz.Constants.PlayerConstants.*;
import static its.pbo.utilz.HelpMethods.canMoveHere;

public class Player extends Entity {
	private BufferedImage[][] image;
	public static final String TYPE = "p";
	private int animTick, animIndex, animSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean left, up, right, down, canMove, isMoving;
	private double playerSpeed = 10f, xSpeed = 0, ySpeed = 0;
	private int[][] lvlData;
	private float xDrawOffset = 3 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	public Player(double x, double y, int width, int height) {
		super(x, y, width, height);
		loadImage();
		initHitBox(x, y, 15 * Game.SCALE, 13 * Game.SCALE);
	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		g.drawImage(image[playerAction][animIndex], (int) (hitBox.x - xDrawOffset), (int) (hitBox.y - yDrawOffset),
				width, height, null);
//		 drawHitBox(g);
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
		if (moving || isMoving)
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
		if (!left && !right && !up && !down)
			return;

		xSpeed = 0;
		ySpeed = 0;
		if (left && !right) {
			xSpeed = -playerSpeed;
		} else if (right && !left) {
			xSpeed = playerSpeed;
		}

		if (up && !down) {
			ySpeed = -playerSpeed;
		} else if (down && !up) {
			ySpeed = playerSpeed;
		}
		canMove = canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData);
		if (canMove) {
			isMoving = true;
			moving = true;
			setAnimation();
			Thread movementThread = new Thread(() -> {
				while (canMove) {
					hitBox.x += xSpeed;
					hitBox.y += ySpeed;
					canMove = canMoveHere(hitBox.x + xSpeed, hitBox.y + ySpeed, hitBox.width, hitBox.height, lvlData);

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				moving = false;
				setAnimation();
			});
			movementThread.start();
		} else {
			moving = false;
			setAnimation();
		}
		isMoving = false;
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
}
