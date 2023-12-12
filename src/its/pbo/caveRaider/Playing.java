package its.pbo.caveRaider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import its.pbo.utilz.LoadSave;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;
	private int xLvlOffset;
	private int yLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int topBorder = (int) (0.2 * Game.GAME_HEIGHT);
	private int bottomBorder = (int) (0.8 * Game.GAME_HEIGHT);
	private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	private int lvlTilesHigh = LoadSave.GetLevelData().length;
	private int maxTilesOffsetX = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxTilesOffsetY = lvlTilesHigh - Game.TILES_IN_HEIGHT;
	private int maxLvlOffsetX = maxTilesOffsetX * Game.TILES_SIZE;
	private int maxLvlOffsetY = maxTilesOffsetY * Game.TILES_SIZE;

	public Playing(Game game) {
		super(game);
		initClasses();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		player = new Player(200, 200, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
	}

	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData());
			checkCloseToBorder();
		} else {
			pauseOverlay.update();
		}
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitBox().x;
		int playerY = (int) player.getHitBox().y;
		int diffX = playerX - xLvlOffset;
		int diffY = playerY - yLvlOffset;

		if (diffX > rightBorder)
			xLvlOffset += diffX - rightBorder;
		else if (diffX < leftBorder)
			xLvlOffset += diffX - leftBorder;

		if (diffY > bottomBorder)
			yLvlOffset += diffY - bottomBorder;
		else if (diffY < topBorder)
			yLvlOffset += diffY - topBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

		if (yLvlOffset > maxLvlOffsetY)
			yLvlOffset = maxLvlOffsetY;
		else if (yLvlOffset < 0)
			yLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g, xLvlOffset, yLvlOffset);
		player.render(g, xLvlOffset, yLvlOffset);
		enemyManager.draw(g,xLvlOffset,yLvlOffset);
		if (paused) {
			g.setColor(new Color(0,0,0,150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
			
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_W:
				player.setUp(true);
				break;
			case KeyEvent.VK_S:
				player.setDown(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_W:
				player.setUp(false);
				break;
			case KeyEvent.VK_S:
				player.setDown(false);
				break;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseDragged(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (paused)
			pauseOverlay.mousePressed(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseReleased(e);

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseMoved(e);

	}

	public void unpauseGame() {
		paused = false;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}