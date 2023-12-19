package its.pbo.caveRaider.Entities;

import static its.pbo.utilz.Constants.EnemyConstants.*;

import its.pbo.caveRaider.Games.Game;
public class Bat extends Enemy{

	public Bat(double x, double y) {
		super(x, y, BAT_WIDTH, BAT_HEIGHT, BAT);
		initHitBox(x, y, (int)(13*Game.SCALE), (int) (13*Game.SCALE));
	}

	public boolean hasCollidedWith(Player player) {
		return this.getHitBox().intersects(player.getHitBox());
		
	}

	public void resetEnemy() {
		// TODO Auto-generated method stub
		hitBox.x = x;
		hitBox.y = y;
	}
	
}
