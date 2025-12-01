package doorgame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
public class StartPanel extends JPanel {
//start screen organization 
    public StartPanel(MainFrame frame) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Door Game");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        title.setBorder(BorderFactory.createEmptyBorder(80, 0, 40, 0));

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 24));

        startButton.addActionListener(e -> frame.startGame());

        add(title);
        add(startButton);
    }

}
