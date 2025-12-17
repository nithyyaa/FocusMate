import javax.swing.*;
import java.awt.*;

public class StreaksPanel extends JPanel {

    private JLabel streakLabel;

    public StreaksPanel() {
        setLayout(new BorderLayout());
        streakLabel = new JLabel("", SwingConstants.CENTER);
        streakLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(streakLabel, BorderLayout.CENTER);

        updateStreak();
    }

    private void updateStreak() {
        if (Main.loggedInUser != null) {
            // For simplicity, using score as streak
            streakLabel.setText("Your current streak: " + Main.loggedInUser.score);
        } else {
            streakLabel.setText("No user logged in.");
        }
    }

    public void refreshStreak() {
        updateStreak();
    }
}
