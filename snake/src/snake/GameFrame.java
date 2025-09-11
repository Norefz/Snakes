package snake;

import javax.swing.*;

public class GameFrame extends JFrame {
    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public GameFrame() {
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Start with menu
        menuPanel = new MenuPanel(this);
        this.add(menuPanel);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    // Called when "Start Game" is clicked
    public void startGame() {
        this.remove(menuPanel);
       gamePanel = new GamePanel();
// pass reference if needed
        this.add(gamePanel);
        this.pack();
        gamePanel.requestFocusInWindow();
        this.revalidate();
        this.repaint();
    }
}
