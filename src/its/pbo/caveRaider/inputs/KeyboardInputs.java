package its.pbo.caveRaider.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import its.pbo.caveRaider.GamePanel;
import its.pbo.caveRaider.GameState;

public class KeyboardInputs implements KeyListener{
//	private Player player;
	private GamePanel gamePanel;
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameState.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		default:
			break;

	}
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		switch(GameState.state) {
		case MENU:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case PLAYING:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		
		}
	}

}
