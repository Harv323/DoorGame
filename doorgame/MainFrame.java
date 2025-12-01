package doorgame;
import javax.swing.JFrame;
public class MainFrame extends JFrame {
	//start screen and game screen
	public MainFrame() {
	        setTitle("Door Game");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setResizable(false);
	        add(new StartPanel(this));   
	        pack();
	        setLocationRelativeTo(null);
	}
	        public void startGame() {
	            getContentPane().removeAll();
	            GamePanel gamePanel = new GamePanel();
	            add(gamePanel);
	            revalidate();
	            repaint();
	            gamePanel.requestFocusInWindow();
	        
	 }
}
