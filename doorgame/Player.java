package doorgame;
import java.awt.Color;
import java.awt.Graphics2D;
public class Player extends GameObject {
	 private int speed = 5;

	    public Player(int x, int y) {
	        super(x, y, 20, 20);
	    }

	    public void move(int dx, int dy, int panelWidth, int panelHeight) {
	        x += dx * speed;
	        y += dy * speed;

	        // keep inside panel bounds
	        if (x < 0) x = 0;
	        if (y < 0) y = 0;
	        if (x + width > panelWidth) x = panelWidth - width;
	        if (y + height > panelHeight) y = panelHeight - height;
	    }
// draw player as a key
	    @Override
	    public void draw(Graphics2D g2) {
	        g2.setColor(new Color(255, 215, 0)); 
	        g2.fillOval(x, y, width, height);
	        g2.setColor(Color.DARK_GRAY);
	        g2.fillOval(x + width/3, y + height/3, width/3, height/3);

	        g2.setColor(new Color(255, 215, 0)); 
	        int shaftX = x + width;
	        int shaftY = y + height/3;
	        int shaftWidth = width * 2;
	        int shaftHeight = height / 3;
	        g2.fillRect(shaftX, shaftY, shaftWidth, shaftHeight);
	        int toothWidth = width / 2;
	        int toothHeight = height / 3;
	        g2.fillRect(shaftX + shaftWidth/3, shaftY + shaftHeight, toothWidth, toothHeight);
	        g2.fillRect(shaftX + shaftWidth/2, shaftY + shaftHeight, toothWidth, toothHeight);
	    }
}
