import javax.swing.*;
import java.awt.*;

public class BreakSessionPanel extends JPanel {
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public BreakSessionPanel() {
        setLayout(new BorderLayout(10, 10));

        // Left buttons: main options
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton chatWithMateButton = new JButton("Chat With Mate");
        JButton gamesButton = new JButton("Games");
        buttonPanel.add(chatWithMateButton);
        buttonPanel.add(gamesButton);
        add(buttonPanel, BorderLayout.WEST);

        // Right dynamic panel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel, BorderLayout.CENTER);

        // Placeholder panel
        JPanel emptyPanel = new JPanel();
        contentPanel.add(emptyPanel, "Empty");
        cardLayout.show(contentPanel, "Empty");

        // Button actions
        chatWithMateButton.addActionListener(e -> {
            ChatPanel chatPanel = new ChatPanel();
            contentPanel.add(chatPanel, "AIChat");
            cardLayout.show(contentPanel, "AIChat");
        });
        gamesButton.addActionListener(e -> showGamesPanel());
    }

    

    private void showGamesPanel() {
        GamesPanel gamesPanel = new GamesPanel();
        contentPanel.add(gamesPanel, "Games");
        cardLayout.show(contentPanel, "Games");
    }

    // For testing independently
    public static void main(String[] args) {
        JFrame frame = new JFrame("Break Session");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.add(new BreakSessionPanel());
        frame.setVisible(true);
    }
}
