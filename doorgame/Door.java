package doorgame;
import java.awt.Graphics2D;
import java.awt.Color;
public class Door extends GameObject {

    private final boolean winningDoor;
    private final String label;
// door properties
    public Door(int x, int y, String label, boolean winningDoor) {
        super(x, y, 70, 110);
        this.label = label;
        this.winningDoor = winningDoor;
    }

    public boolean isWinningDoor() {
        return winningDoor;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillRect(x, y, width, height);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, width, height);
        g2.drawString(label, x + 20, y + height / 2);
    }

}
