package its.pbo.caveRaider.Objects;

import its.pbo.caveRaider.Games.Game;
import its.pbo.utilz.Constants;

public class Dots extends GameObject {

	private float hoverOffset;
	private int maxHoverOffset, hoverDir = 1;

	public Dots(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;

		initHitbox(12, 17);

		xDrawOffset = (int) (-3 * Game.SCALE);
		yDrawOffset = (int) (-1 * Game.SCALE);

		maxHoverOffset = (int) (10 * Game.SCALE);
	}

	public void update() {
		updateAnimationTick();
		updateHover();
	}

	private void updateHover() {
		hoverOffset += (0.075f * Game.SCALE * hoverDir);

		if (hoverOffset >= maxHoverOffset)
			hoverDir = -1;
		else if (hoverOffset < 0)
			hoverDir = 1;

		hitbox.y = y + hoverOffset;
	}

}
