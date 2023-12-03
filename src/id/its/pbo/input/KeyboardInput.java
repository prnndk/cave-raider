package id.its.pbo.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import id.its.pbo.cave_raider.Game;

public class KeyboardInput implements KeyListener {
	private Game game;
	public KeyboardInput(Game game) {
		this.game = game;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		game.getPlayer().setDirection(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
