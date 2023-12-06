package its.pbo.caveRaider;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import its.pbo.utilz.LoadSave;

public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private double menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 3 - 375;
		menuY = (int) (45 * Game.SCALE);

	}

    private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 3 - 85, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 3 - 85, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 3 - 85, (int) (290 * Game.SCALE), 2, GameState.QUIT);
	}



    public double getMenuWidth() {
        return menuWidth;
    }

    public double getMenuHeight() {
        return menuHeight;
    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        draw(g, menuX, menuY);
    }

    // Updated draw method with additional position arguments
public void draw(Graphics g, double x, double y) {
	g.drawImage(backgroundImg, (int) x, (int) y, (int) menuWidth, (int) menuHeight, null);

	for (MenuButton mb : buttons)
		mb.draw(g);
}

@Override
public void mousePressed(MouseEvent e) {
	for (MenuButton mb : buttons) {
		if (isIn(e, mb)) {
			mb.setMousePressed(true);
		}
	}
}


    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameState.state = GameState.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
} 