package its.pbo.caveRaider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game implements Runnable {
	public static final int FPS = 120;
	public static final int TILES_DEFAULT_SIZE = 36;
	public static final float SCALE = 2f;
	public static final int TILES_WIDTH = 36;
	public static final int TILES_HEIGHT = 46;
	public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = TILES_SIZE * TILES_WIDTH;
	public static final int GAME_HEIGHT = TILES_SIZE * TILES_HEIGHT;
	
	private final int UPS = 200;
	public static final int WIDTH = 720;
	public static final int HEIGHT = 1080;
	private GameFrame frame;
	private GamePanel panel;
	private Thread gameThread;
	
	private Player player;
	private LevelManager levelManager;
	
	public Game() {
	initClasses();
	
	panel = new GamePanel(this);
	frame = new GameFrame(panel);
	panel.requestFocus();

	startGameLoop();
	}
	public static void main(String[] args) {
		new Game();
	}
	
	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200,200,(int) (20*SCALE), (int)(20*SCALE));
		player.loadLvlData(levelManager.getCurrentLevelData().getLevelData());
	}
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void update() {
		player.update();
		levelManager.update();
	}
	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				panel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
		}
	}
	public void windowsFocusLost() {
		player.resetDirBooleans();
	}
	public Player getPlayer() {
		return player;
	}

}
