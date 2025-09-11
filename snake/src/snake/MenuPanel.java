package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {

    private Image backgroundimage;

    public MenuPanel(GameFrame frame) {
        this.setPreferredSize(new Dimension(600, 600));
        this.setLayout(new GridBagLayout()); // for centering things

        // Load background image
        backgroundimage = new ImageIcon(getClass().getResource("/Pictures/letsgoo.jpg")).getImage();

        // === TITLE ===
        JLabel title = new JLabel("SNAKE GAME");
        title.setFont(new Font("Ink Free", Font.BOLD, 60));
        title.setForeground(Color.RED); // text color
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // === BUTTON ===
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Ink Free", Font.BOLD, 30));
        startButton.setBackground(new Color(50, 205, 50));  // green background
        startButton.setForeground(Color.WHITE);             // white text
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createRaisedBevelBorder());

        // Add hover effect
        startButton.addChangeListener(e -> {
            if (startButton.getModel().isRollover()) {
                startButton.setBackground(new Color(34, 139, 34)); // darker green on hover
            } else {
                startButton.setBackground(new Color(50, 205, 50));
            }
        });

        startButton.addActionListener(e -> frame.startGame());

        // === LAYOUT ===
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 20, 0);

        // Title at top
        gbc.gridy = 0;
        this.add(title, gbc);

        // Button below title
        gbc.gridy = 1;
        this.add(startButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundimage != null) {
            g.drawImage(backgroundimage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

