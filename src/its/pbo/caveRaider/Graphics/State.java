package its.pbo.caveRaider.Graphics;
import java.awt.event.MouseEvent;

import its.pbo.caveRaider.Games.Game;


public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}
	

	public Game getGame() {
		return game;
	}
}