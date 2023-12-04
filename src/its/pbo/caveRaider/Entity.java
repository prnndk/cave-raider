package its.pbo.caveRaider;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
	protected double x,y;
	protected int width,height;
	protected Rectangle2D.Double hitBox;
	public Entity(double x2,double y2,int width,int height) {
		this.x = x2;
		this.y = y2;
		this.width = width;
		this.height = height;
	}
	protected void drawHitBox(Graphics g) {
		g.setColor(Color.cyan);
		g.drawRect((int) hitBox.x,(int)hitBox.y,(int) hitBox.width,(int) hitBox.height);
	}
	
	protected void initHitBox(double x,double y,double width,double height) {
		hitBox = new Rectangle2D.Double(x,y,width,height);
	}
	
//	public void updateHitBox() {
//		hitBox.x = (int)x;
//		hitBox.y = (int)y;
//	}
	public Rectangle2D.Double getHitBox() {
		return hitBox;
	}
	
}
