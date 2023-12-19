package its.pbo.caveRaider.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import its.pbo.caveRaider.Games.Game;
import its.pbo.utilz.Constants.ObjectConstanst;

import static its.pbo.utilz.Constants.*;
import static its.pbo.utilz.Constants.ObjectConstanst.CANNON_LEFT;
import static its.pbo.utilz.Constants.ObjectConstanst.CANNON_RIGHT;


public class GameObject {

	protected int x, y, objType;
	protected Rectangle2D.Double hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;

	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;
				if (  objType == ObjectConstanst.CANNON_LEFT || objType == ObjectConstanst.CANNON_RIGHT)
					doAnimation = false;
			}
		}
	}

	public void reset() {
		aniIndex = 0;
		aniTick = 0;
		active = true;
		
		hitbox.x = x;
		hitbox.y = y;
		
		if (objType == CANNON_LEFT || objType == CANNON_RIGHT) {
			doAnimation = false;			
		}
		else {
			doAnimation = true;			
		}
	}

	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Double(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

	public void drawHitbox(Graphics g, int xLvlOffset, int yLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y - yLvlOffset, (int) hitbox.width, (int) hitbox.height);
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Double getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getAniTick() {
		return aniTick;
	}

}