package its.pbo.caveRaider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import its.pbo.caveRaider.inputs.KeyboardInputs;

public class GamePanel extends JPanel {
	private Game game;
	public GamePanel(Game game) {
		this.game = game;
		setPanelSize();
		addKeyListener(new KeyboardInputs(this));
	}
	private void setPanelSize() {
		setPreferredSize(new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT));
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		for (int i = 0; i < 64; i++)
			for (int j = 0; j < 40; j++)
				g.fillRect(i * 20, j * 20, 20, 20);
		game.render(g);
	}
	public Game getGame() {
		return game;
	}
}
