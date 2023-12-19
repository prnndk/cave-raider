package its.pbo.caveRaider.Graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import its.pbo.caveRaider.Games.Game;
import its.pbo.caveRaider.Games.GameState;
import its.pbo.caveRaider.Games.Playing;

public class GameOver {

	private Playing playing;
	public GameOver(Playing playing) {
		this.playing = playing;
	}
	public void draw(Graphics g) {
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString("Game Over", Game.GAME_WIDTH/2, 150);
		g.drawString("Press Esc To Main Menu", Game.GAME_WIDTH/2, 300);
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			GameState.state = GameState.MENU;
		}
	}
}
