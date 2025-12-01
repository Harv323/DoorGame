package doorgame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GamePanel extends JPanel implements KeyListener, ActionListener {

	    public static final int WIDTH = 800;
	    public static final int HEIGHT = 600;

	    private Player player;
	    private List<Door> doors = new ArrayList<>();
	    private Coin coin;

	    private GameState state = GameState.PLAYING;
	    private int room = 1; // 1 or 2
	    private int coinsCollected = 0;
	    private Timer timer;
	    private Random random = new Random();
	    private Door activeDoor = null;

	    public GamePanel() {
	        setPreferredSize(new Dimension(WIDTH, HEIGHT));
	        setBackground(Color.DARK_GRAY);
	        setFocusable(true);
	        addKeyListener(this);

	        initRoom1();

	        timer = new Timer(16, this); // ~60 FPS
	        timer.start();
	    }

	    // room setup

	    private void initRoom1() {
	        state = GameState.PLAYING;
	        room = 1;
	        doors.clear();

	        // player starting position
	        player = new Player(100, HEIGHT - 80);

	        // 2 doors, one winning
	        int winningIndex = random.nextInt(2); // 0 or 1
	        // positions for 2 doors
	        int doorY = 150;
	        int[] xs = {250, 480};

	        for (int i = 0; i < 2; i++) {
	            boolean isWin = (i == winningIndex);
	            Door d = new Door(xs[i], doorY, "Door " + (i + 1), isWin);
	            doors.add(d);
	        }

	        // coin location
	        int coinX = 60 + random.nextInt(WIDTH - 120);    // inside the room bounds
	        int coinY = 120 + random.nextInt(HEIGHT - 220);  // inside the room bounds
	        coin = new Coin(coinX, coinY);
	    }

	    private void initRoom2() {
	        state = GameState.PLAYING;
	        room = 2;
	        doors.clear();

	        // keep same player position
	        player = new Player(100, HEIGHT - 80);

	        // 3 doors, one winning
	        int winningIndex = random.nextInt(3); // 0..2
	        int doorY = 150;
	        int[] xs = {180, 360, 540};

	        for (int i = 0; i < 3; i++) {
	            boolean isWin = (i == winningIndex);
	            Door d = new Door(xs[i], doorY, "Door " + (i + 1), isWin);
	            doors.add(d);
	        }

	        // coin for room 2
	        int coinX = 60 + random.nextInt(WIDTH - 120);    // inside the room bounds
	        int coinY = 120 + random.nextInt(HEIGHT - 220);  // inside the room bounds
	        coin = new Coin(coinX, coinY);
	    }
	    private void checkDoorOverlap() {
	        if (state != GameState.PLAYING) return;

	        Door overlapped = null;

	        for (Door d : doors) {
	            if (player.getBounds().intersects(d.getBounds())) {
	                overlapped = d;
	                break;
	            }
	        }

	        // If we just moved onto a new door, trigger the prompt
	        if (overlapped != null && overlapped != activeDoor) {
	            activeDoor = overlapped;
	            pickDoor(overlapped); // shows the YES/NO dialog
	        }

	        // If we’re not on any door, reset
	        if (overlapped == null) {
	            activeDoor = null;
	        }
	        }
	 
	  
	    // game mechanics

	    private void pickDoor(Door door) {
	        // prompt
	        int result = JOptionPane.showConfirmDialog(
	                this,
	                "Are you sure you want to select this door?",
	                "Confirm Door",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (result == JOptionPane.NO_OPTION) {
	            // go back to guiding cursor
	            return;
	        }

	        // YES clicked
	        if (door.isWinningDoor()) {
	            if (room == 1) {
	                // go to next room
	                initRoom2();
	            } else {
	                // room 2 correct, win game
	                state = GameState.VICTORY;
	                endGame(true);
	            }
	        } else {
	            // wrong door, lose
	            state = GameState.GAME_OVER;
	            endGame(false);
	        }
	    }

	    private void endGame(boolean won) {
	        String msg = won ? "You picked the correct doors!\n" : "Wrong door!\n";
	        msg += "Coins collected: " + coinsCollected + "\n\nPlay again?";

	        int answer = JOptionPane.showConfirmDialog(
	                this,
	                msg,
	                won ? "You Win!" : "Game Over",
	                JOptionPane.YES_NO_OPTION
	        );

	        if (answer == JOptionPane.YES_OPTION) {
	            // reset everything to room 1
	            coinsCollected = 0;
	            initRoom1();
	        } else {
	            // just stop the game loop (window itself stays open)
	            timer.stop();
	        }
	    }

	    private void checkCoinPickup() {
	        if (coin != null && !coin.isCollected()) {
	            if (player.getBounds().intersects(coin.getBounds())) {
	                coin.collect();
	                coinsCollected++;
	            }
	        }
	    }

	    // ---------- TIMER / PAINT ----------

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        // we don't have moving enemies, so just repaint
	        repaint();
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(
	                RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON
	        );

	        // room background 
	        g2.setColor(Color.GRAY);
	        g2.fillRect(40, 100, WIDTH - 80, HEIGHT - 160);
	        g2.setColor(Color.BLACK);
	        g2.drawRect(40, 100, WIDTH - 80, HEIGHT - 160);

	        // draw doors
	        for (Door d : doors) {
	            d.draw(g2);
	        }

	        // draw coin
	        if (coin != null) {
	            coin.draw(g2);
	        }

	        // draw player
	        player.draw(g2);

	        // HUD
	        g2.setColor(Color.WHITE);
	        g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
	        g2.drawString("Room: " + room, 20, 30);
	        g2.drawString("Coins: " + coinsCollected, 20, 55);
	        g2.drawString("Move with Arrow Keys. Move to a door to choose it.", 250, 30);
	    }

	    // input

	    @Override
	    public void keyPressed(KeyEvent e) {
	        if (state != GameState.PLAYING) return;

	        int code = e.getKeyCode();
	        int dx = 0;
	        int dy = 0;

	        if (code == KeyEvent.VK_LEFT) {
	            dx = -1;
	        } else if (code == KeyEvent.VK_RIGHT) {
	            dx = 1;
	        } else if (code == KeyEvent.VK_UP) {
	            dy = -1;
	        } else if (code == KeyEvent.VK_DOWN) {
	            dy = 1;
	        }

	        if (dx != 0 || dy != 0) {
	            player.move(dx, dy, getWidth(), getHeight());
	            checkCoinPickup();
	            checkDoorOverlap();
	            repaint();
	        }
	    }

	  
	    // unused methods
	    @Override public void keyReleased(KeyEvent e) {}
	    @Override public void keyTyped(KeyEvent e) {}
	    
}
