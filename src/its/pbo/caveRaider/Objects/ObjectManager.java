package its.pbo.caveRaider.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import its.pbo.caveRaider.Entities.Player;
import its.pbo.caveRaider.Games.Level;
import its.pbo.caveRaider.Games.Playing;
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
	private ArrayList<Dots> dots;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private ArrayList<Projectile> projectiles = new ArrayList<>();

	public ObjectManager(Playing playing) {
		this.playing = playing;
		loadImgs();
	}

	// public void checkSpikesTouched(Player p) {
	// for (Spike s : spikes)
	// if (s.isActive()) {
	// if (s.getHitbox().intersects(p.getHitBox())) {
	// playing.setGameOver(true);
	// }
	// }
	// }

	public void checkObjectTouched(Double hitBox) {
		for (Dots p : dots)
			if (p.isActive()) {
				if (hitBox.intersects(p.getHitbox())) {
					p.setActive(false);
				}
			}
	}

	public void loadObjects(Level newLevel) {
		dots = new ArrayList<>(newLevel.getPotions());
		spikes = newLevel.getSpikes();
		cannons = newLevel.getCannons();
		projectiles.clear();
	}

	private void loadImgs() {
		BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.DOTS_ATLAS);
		potionImgs = new BufferedImage[3][7];

		for (int j = 0; j < potionImgs.length; j++)
			for (int i = 0; i < potionImgs[j].length; i++)
				potionImgs[j][i] = potionSprite.getSubimage(12 * i, 12 * j, 12, 12);

		spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

		cannonImgs = new BufferedImage[7];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CANNON_ATLAS);

		for (int i = 0; i < cannonImgs.length; i++)
			cannonImgs[i] = temp.getSubimage(i * 40, 0, 40, 26);

		cannonBallImg = LoadSave.GetSpriteAtlas(LoadSave.CANNON_BALL);

	}

	public void update(int[][] lvlData, Player player) {
		for (Dots p : dots) {
			if (p.isActive()) {
				p.update();
			}

		}

		updateCannons(lvlData, player);
		updateProjectiles(lvlData, player);
//		updateSpike(lvlData, player);
	}

	private void updateProjectiles(int[][] lvlData, Player player) {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.updatePos();
				if (p.getHitbox().intersects(player.getHitBox())) {
					playing.setGameOver(true);
				} else if (IsProjectileHittingLevel(p, lvlData))
					p.setActive(false);
			}
	}

	private void updateCannons(int[][] lvlData, Player player) {
		for (Cannon c : cannons) {
			if (!c.doAnimation) {
				c.setAnimation(true);
			}
			c.update();
			if (c.getAniIndex() == 4 && c.getAniTick() == 0) {
				shootCannon(c);
			}
		}
	}

	private void shootCannon(Cannon c) {
		int dir = 1;
		if (c.getObjType() == CANNON_LEFT) {
			dir = -1;
		}

		projectiles.add(new Projectile((int) c.getHitbox().x, (int) c.getHitbox().y, dir));

	}

	public void updateSpike(int[][] lvlData, Player player) {
		for (Spike s : spikes) {
			if (s.getHitbox().intersects(player.getHitBox())) {
				s.setActive(true);
			}
			if (s.isActive()) {
				if (s.getHitbox().intersects(player.getHitBox())) {
					playing.setGameOver(true);
				}
			}
		}
	}

	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		drawPotions(g, xLvlOffset, yLvlOffset);
		drawTraps(g, xLvlOffset, yLvlOffset);
		drawCannons(g, xLvlOffset, yLvlOffset);
		drawProjectiles(g, xLvlOffset, yLvlOffset);
	}

	private void drawProjectiles(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Projectile p : projectiles)
			if (p.isActive())
				g.drawImage(cannonBallImg, (int) (p.getHitbox().x - xLvlOffset), (int) (p.getHitbox().y - yLvlOffset),
						CANNON_BALL_WIDTH, CANNON_BALL_HEIGHT, null);

	}

	private void drawCannons(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Cannon c : cannons) {
			int x = (int) (c.getHitbox().x - xLvlOffset);
			int y = (int) (c.getHitbox().y - yLvlOffset);
			int width = CANNON_WIDTH;

			if (c.getObjType() == CANNON_RIGHT) {
				x += width;
				width *= -1;
			}

			g.drawImage(cannonImgs[c.getAniIndex()], x, y, width, CANNON_HEIGHT, null);
		}

	}

	private void drawTraps(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Spike s : spikes) {
			if (s.isActive()) {
				g.drawImage(spikeImg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - yLvlOffset),
						SPIKE_WIDTH, SPIKE_HEIGHT, null);
			}
		}

	}

	private void drawPotions(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Dots d : dots)
			if (d.isActive()) {
				int type = 0;
				if (d.getObjType() == COIN) {
					type = 1;
				}
				g.drawImage(potionImgs[type][d.getAniIndex()],
						(int) (d.getHitbox().x - d.getxDrawOffset() - xLvlOffset),
						(int) (d.getHitbox().y - d.getyDrawOffset() - yLvlOffset), POTION_WIDTH, POTION_HEIGHT, null);
			}
	}

	public void resetAllObjects() {
		loadObjects(playing.getLevelManager().getCurrentLevel());
		for (Dots p : dots) {
			p.reset();
		}
		for (Cannon c : cannons) {
			c.reset();
		}
	}
}