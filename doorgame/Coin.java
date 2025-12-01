package doorgame;
import java.awt.Color;
import java.awt.Graphics2D;

public class Coin extends GameObject {
// coin properties
    private boolean collected = false;

    public Coin(int x, int y) {
        super(x, y, 20, 20);
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (collected) return;
        g2.setColor(Color.YELLOW);
        g2.fillOval(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, width, height);
    }

}
