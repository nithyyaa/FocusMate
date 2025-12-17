
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LeaderboardPanel extends JPanel {
    private JTextArea leaderboardArea;

    public LeaderboardPanel() {
        setLayout(new BorderLayout());
        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        add(new JScrollPane(leaderboardArea), BorderLayout.CENTER);
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        leaderboardArea.setText("");
        Users users = new Users("users.txt");
        List<Users.User> allUsers = users.getAllUsers();

        if (allUsers.isEmpty()) {
            leaderboardArea.setText("No users yet!");
            return;
        }

        // Sort users by score in descending order (highest streak first)
        allUsers.sort((u1, u2) -> Integer.compare(u2.score, u1.score));

        leaderboardArea.append("🏆 LEADERBOARD - Top Streaks 🏆\n");
        leaderboardArea.append("═══════════════════════════════════\n\n");

        int rank = 1;
        for (Users.User user : allUsers) {
            String medal = "";
            if (rank == 1) medal = "🥇 ";
            else if (rank == 2) medal = "🥈 ";
            else if (rank == 3) medal = "🥉 ";
            else medal = rank + ". ";

            leaderboardArea.append(medal + user.firstName + " " + user.lastName + 
                                 " - Streak: " + user.score + " 🔥\n");
            rank++;
        }
    }

    public void refreshLeaderboard() {
        loadLeaderboard();
    }
}
