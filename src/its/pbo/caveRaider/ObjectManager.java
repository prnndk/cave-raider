package its.pbo.caveRaider;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import its.pbo.utilz.LoadSave;
import static its.pbo.utilz.Constants.ObjectConstanst.*;
import static its.pbo.utilz.HelpMethods.CanCannonSeePlayer;
import static its.pbo.utilz.HelpMethods.IsProjectileHittingLevel;
import static its.pbo.utilz.Constants.Projectiles.*;

public class ObjectManager {

	private Playing playing;
    
	private BufferedImage[][] potionImgs;
	private BufferedImage[] cannonImgs;
	private BufferedImage spikeImg, cannonBallImg;
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private ArrayList<Projectile> projectiles = new ArrayList<>();

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}




    public void checkSpikesTouched(Player p) {
        for (Spike s : spikes)
            if (s.getHitbox().intersects(p.getHitBox())) {
                playing.setGameOver(true);
            }
    }

	public void checkObjectTouched(Double hitBox) {
		for (Potion p : potions)
			if (p.isActive()) {
				if (hitBox.intersects(p.getHitbox())) {
					p.setActive(false);
				}
			}
	}



	public void loadObjects(Level newLevel) {
		potions = new ArrayList<>(newLevel.getPotions());
		spikes = Level.getSpikes();
		cannons = newLevel.getCannons();
		projectiles.clear();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
		potionImgs = new BufferedImage[2][7];

		for (int j = 0; j < potionImgs.length; j++)
			for (int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

		cannonImgs = new BufferedImage[7];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

		for (int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);

		cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);

	}

	public void update(int[][] lvlData, Player player) {
		for (Potion p : potions)
			if (p.isActive())
				p.update();

		updateCannons(lvlData, player);
		updateProjectiles(lvlData, player);
	}

	private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitBox())) {
					p.setActive(false);
				} else if (IsProjectileHittingLevel(p, lvlData))
					p.setActive(false);
			}
	}

	private boolean isPlayerInRange(Cannon c, Player player) {
		int absValue = (int) Math.abs(player.getHitBox().x - c.getHitbox().x);
		return absValue <= Game.TILES_SIZE * 5;
	}

	private boolean isPlayerInfrontOfCannon(Cannon c, Player player) {
		if (c.getObjType() == CANNON_LEFT) {
			if (c.getHitbox().x > player.getHitBox().x)
				return true;

		} else if (c.getHitbox().x < player.getHitBox().x)
			return true;
		return false;
	}

	private void updateCannons(int[][] lvlData, Player player) {
		for (Cannon c : cannons) {
			if (!c.doAnimation)
				if (c.getTileY() == player.getTileY())
					if (isPlayerInRange(c, player))
						if (isPlayerInfrontOfCannon(c, player))
							if (CanCannonSeePlayer(lvlData, player.getHitBox(), c.getHitbox(), c.getTileY()))
								c.setAnimation(true);

			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0)
				shootCannon(c);
		}
	}

	private void shootCannon(Cannon c) {
		int dir = 1;
		if (c.getObjType() == CANNON_LEFT)
			dir = -1;

		projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));

	}

	public void draw(Graphics g, int xLvlOffset) {
		drawPotions(g, xLvlOffset);
		drawTraps(g, xLvlOffset);
		drawCannons(g, xLvlOffset);
		drawProjectiles(g, xLvlOffset);
	}

	private void drawProjectiles(Graphics g, int xLvlOffset) {
		for (Projectile p : projectiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y), CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);

	}

	private void drawCannons(Graphics g, int xLvlOffset) {
		for (Cannon c : cannons) {
			int x = (int) (c.getHitbox().x - xLvlOffset);
			int width = CANNON_WIDTH;

			if (c.getObjType() == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}

			g.drawImage(cannonImgs[c.getAniIndex()], x, (int) (c.getHitbox().y), width, CANNON_HEIGHT, null);
		}

	}

	private void drawTraps(Graphics g, int xLvlOffset) {
		for (Spike s : spikes)
			g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}


	private void drawPotions(Graphics g, int xLvlOffset) {
		for (Potion p : potions)
			if (p.isActive()) {
				int type = 0;
				if (p.getObjType() == RED_POTION)
					type = 1;
				g.drawImage(potionImgs[type][p.getAniIndex()], (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int) (p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT,
						null);
			}
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Potion p : potions)
			p.reset();
		for (Cannon c : cannons)
			c.reset();
	}
}