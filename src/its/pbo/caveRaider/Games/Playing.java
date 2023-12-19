package its.pbo.caveRaider.Games;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D.Double;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import java.awt.geom.Rectangle2D;

import its.pbo.caveRaider.Entities.Bat;
import its.pbo.caveRaider.Entities.EnemyManager;
import its.pbo.caveRaider.Entities.Goal;
import its.pbo.caveRaider.Entities.Player;
import its.pbo.caveRaider.Graphics.GameOverOverlay;
import its.pbo.caveRaider.Graphics.LevelCompletedOverlay;
import its.pbo.caveRaider.Graphics.PauseOverlay;
import its.pbo.caveRaider.Graphics.State;
import its.pbo.caveRaider.Graphics.Statemethods;
import its.pbo.caveRaider.Objects.ObjectManager;
import its.pbo.utilz.LoadSave;

public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private Goal goal;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay GameOverOverlay;
    private LevelCompletedOverlay levelCompleteOverlay;
    private boolean paused = false;
    private boolean gameOver = false;
    private boolean lvlCompleted;
    private int xLvlOffset;
    private int yLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int topBorder = (int) (0.2 * Game.GAME_HEIGHT);
    private int bottomBorder = (int) (0.8 * Game.GAME_HEIGHT);
    private int maxLvlOffsetX;
    private int maxLvlOffsetY;


    public Playing(Game game) {
        super(game);
        initClasses();
        
        calcLvlOffset();
        loadStartLevel();
    }
    
    
    
    private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffsetX();
		maxLvlOffsetY = levelManager.getCurrentLevel().getLvlOffsetY();
	}

	public void loadNextLevel() {
    	resetAll();
    	levelManager.loadNextLevel();
    	player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    	goal.setSpawn(levelManager.getCurrentLevel().getGoalSpawn());
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        
        goal = new Goal(200, 200, (int) (30*Game.SCALE), (int) (30*Game.SCALE));
        player = new Player(200, 200, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE),this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        goal.setSpawn(levelManager.getCurrentLevel().getGoalSpawn());
        pauseOverlay = new PauseOverlay(this);
        GameOverOverlay = new GameOverOverlay(this);
        levelCompleteOverlay = new LevelCompletedOverlay(this);
    }

    @Override
    public void update() {
    	if(paused) {
    		pauseOverlay.update();
    	}else if(lvlCompleted) {
    		levelCompleteOverlay.update();
    	}else if (!gameOver) {
    		levelManager.update();
            player.update();
            goal.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder();
            checkPlayerBatCollisions();
            checkGoal();
    	}
    }

    public void checkPotionTouched(Rectangle2D.Double hitBox) {
		objectManager.checkObjectTouched(hitBox);
	}

	public void checkSpikesTouched(Player p) {
		objectManager.checkSpikesTouched(p);
	}

    private void checkPlayerBatCollisions() {
        Double playerHitBox = player.getHitBox();

        for (Bat bat : enemyManager.getBats()) {
            Double batHitBox = bat.getHitBox();

            if (playerHitBox.intersects(batHitBox)) {
                handlePlayerBatCollision(bat);
            }
        }
    }

	private void handlePlayerBatCollision(Bat bat) {
		if (bat.hasCollidedWith(player)) {
			setGameOver(true);
		}
	}
	
	private void checkGoal() {
		if(player.getHitBox().intersects(goal.getHitBox())) {
			if(goal.touchPlayer(player)) {
				setLevelCompleted(true);
			}
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
    goal.render(g, xLvlOffset, yLvlOffset);
    enemyManager.draw(g, xLvlOffset, yLvlOffset);
    objectManager.draw(g, xLvlOffset,yLvlOffset);
    if (paused) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        pauseOverlay.draw(g);
        }else if(gameOver) {
        	GameOverOverlay.draw(g);
        }else if(lvlCompleted) {        	
        	levelCompleteOverlay.draw(g);
        }
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
    	if(gameOver) {
    		GameOverOverlay.keyPressed(e);
    	}else if(!player.isMoving()){
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
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	if(!gameOver) {
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
      
    }

    public void mouseDragged(MouseEvent e) {
    	if(!gameOver)
    		if (paused)
    			pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	if(!gameOver) {
    		if (paused) {
    			pauseOverlay.mousePressed(e);
    		}
    		else if(lvlCompleted) {
    			levelCompleteOverlay.mousePressed(e);
    		}
    	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	if(!gameOver) {
    		if (paused) {
    			pauseOverlay.mouseReleased(e);
    			
    		}else if(lvlCompleted){
    			levelCompleteOverlay.mouseReleased(e);
    		}
    	}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	if(!gameOver) {
    		if (paused)
    			pauseOverlay.mouseMoved(e);
    		else if(lvlCompleted)
    			levelCompleteOverlay.mouseMoved(e);
    		
    	}
    }
    
    public void setLevelCompleted(boolean levelCompleted) {
    	this.lvlCompleted = levelCompleted;
    }
    
    public void setMaxLvlOffsetX(int lvlOffset) {
    	this.maxLvlOffsetX = lvlOffset;
    }
    
    public void setMaxLvlOffsetY(int lvlOffset) {
    	this.maxLvlOffsetY = lvlOffset;
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
    
    public EnemyManager getEnemyManager() {
    	return enemyManager;
    }
    
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

    public ObjectManager getObjectManager() {
		return objectManager;
	}

    public LevelManager getLevelManager() {
		return levelManager;
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
	}

}
