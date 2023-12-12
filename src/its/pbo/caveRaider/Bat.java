package its.pbo.caveRaider;

import static its.pbo.utilz.Constants.EnemyConstants.*;
public class Bat extends Enemy{

	public Bat(double x, double y) {
		super(x, y, BAT_WIDTH, BAT_HEIGHT, BAT);
		initHitBox(x, y, (int)(13*Game.SCALE), (int) (13*Game.SCALE));
	}

}
