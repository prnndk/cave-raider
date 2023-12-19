package its.pbo.caveRaider.Entities;

import static its.pbo.utilz.Constants.EnemyConstants.*;
import static its.pbo.utilz.Constants.Directions.*;	
import static its.pbo.utilz.HelpMethods.*;

import its.pbo.caveRaider.Games.Game;

public abstract class Enemy extends Entity {
	private int animationIndex,enemyState,enemyType;
	private int animationTick, animationSpeed = 25;
	
	private float enemySpeed =  0.35f *Game.SCALE;
	private int enemyDirection = LEFT;
	
	public Enemy(double x, double y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		initHitBox(x, y, width, height);
	}
	private void updateAnimationTick() {
		animationTick++;
		if(animationTick>=animationSpeed) {
			animationTick=0;
			animationIndex++;
			if(animationIndex >= getSpriteAmount(enemyType,enemyState)) {
				animationIndex =0;
			}
		}
	}
	public void update(int[][] lvlData) {
		updateMove(lvlData);
		updateAnimationTick();
	}
	
	private void updateMove(int[][] lvlData) {
		switch(enemyState) {
		case IDLE:
			enemyState = RUNNING;
			break;
		case RUNNING:
			float xSpeed = 0;
			if(enemyDirection == LEFT)
				xSpeed = -enemySpeed;
			else
				xSpeed = enemySpeed;
			
			if(canMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
				hitBox.x += xSpeed;
			}else {
				changeEnemyDirection();
			}
			break;
		}
	}
	
	private void changeEnemyDirection() {
		if(enemyDirection == LEFT) {
			enemyDirection = RIGHT;
		}else {
			enemyDirection = LEFT;
		}
	}
	public int getAnimationIndex() {
		return animationIndex;
	}
	public int getEnemyState() {
		return enemyState;
	}
}
