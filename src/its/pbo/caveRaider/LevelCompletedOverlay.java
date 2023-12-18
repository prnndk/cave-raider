package its.pbo.caveRaider;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import its.pbo.utilz.LoadSave;
import static its.pbo.utilz.Constants.UI.URMButtons.*;

public class LevelCompletedOverlay {
private Playing playing;
private UrmButton menu,next;
private BufferedImage img;
private int bgX,bgY,bgW,bgH;
public LevelCompletedOverlay(Playing playing) {
	this.playing = playing;
	initImg();
	initButton();
}
private void initButton() {
	int menuX = (int) (290*Game.SCALE);
	int nextX = (int) (340*Game.SCALE);
	int y = (int) (160*Game.SCALE);
	next = new UrmButton(nextX, y, URM_SIZE, URM_SIZE, 0);
	menu = new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);
}
private void initImg() {
	img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
	bgW = (int) (img.getWidth()*Game.SCALE);
	bgH = (int) (img.getHeight()*Game.SCALE);
	bgX = Game.GAME_WIDTH/3 - 35;
	bgY = (int) (25*Game.SCALE);
}

public void draw(Graphics g) {
	g.drawImage(img, bgX, bgY, bgW, bgH, null);
	next.draw(g);
	menu.draw(g);
}
public void update() {
	next.update();
	menu.update();
}
private boolean isIn(UrmButton b,MouseEvent e) {
	return b.getBounds().contains(e.getX(),e.getY());
}
public void mouseMoved(MouseEvent e) {
	next.setMouseOver(false);
	menu.setMouseOver(false);
	
	if(isIn(menu,e)) {
		menu.setMouseOver(true);
	}else if(isIn(next,e)) {
		next.setMouseOver(true);
	}
	
}
public void mouseReleased(MouseEvent e) {
	if(isIn(menu,e)) {
		if(menu.isMousePressed()) {
			playing.resetAll();
			GameState.state = GameState.MENU;
		}
//		menu.setMouseOver(true);
	}else if(isIn(next,e)) {
		if(next.isMousePressed()) {
			playing.loadNextLevel();
		}
	}
	menu.resetBools();
	next.resetBools();
}
public void mousePressed(MouseEvent e) {
	if(isIn(menu,e)) {
		menu.setMousePressed(true);
	}else if(isIn(next,e)) {
		next.setMousePressed(true);
	}
}

}

