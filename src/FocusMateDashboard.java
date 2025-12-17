import javax.swing.*;
import java.awt.*;

public class FocusMateDashboard extends JPanel {
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JTextArea chatArea;
    private JLabel welcomeLabel;
    private StreaksPanel streaksPanel;
    private LeaderboardPanel leaderboardPanel;

    public FocusMateDashboard() {
        setLayout(new BorderLayout());

        // ===== Left Panel =====
        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton dashboardBtn = new JButton("Dashboard");
        JButton pomodoroBtn = new JButton("Pomodoro");
        JButton breakSessionBtn = new JButton("Break Session");
        JButton streaksBtn = new JButton("Streaks");
        JButton todoBtn = new JButton("To-Do List");
        JButton leaderboardBtn = new JButton("Leaderboard");
        JButton signOutBtn = new JButton("Sign Out");

        leftPanel.add(dashboardBtn);
        leftPanel.add(pomodoroBtn);
        leftPanel.add(breakSessionBtn);
        leftPanel.add(streaksBtn);
        leftPanel.add(todoBtn);
        leftPanel.add(leaderboardBtn);
        leftPanel.add(signOutBtn);

        add(leftPanel, BorderLayout.WEST);

        // ===== Right Panel =====
        rightPanel = new JPanel();
        cardLayout = new CardLayout();
        rightPanel.setLayout(cardLayout);

        // Dashboard Panel
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        dashboardPanel.add(welcomeLabel, BorderLayout.CENTER);
        updateWelcomeLabel();

        // Actual Panels
        PomodoroPanel pomodoroPanel = new PomodoroPanel();
        streaksPanel = new StreaksPanel();
        CalendarToDoPanel todoPanel = new CalendarToDoPanel();
        leaderboardPanel = new LeaderboardPanel();

        // Break Session Panel
        JPanel breakSessionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;

        JButton chatBtn = new JButton("Chat with Mate");
        JButton gameBtn = new JButton("Play Game");
        breakSessionPanel.add(chatBtn, gbc);
        gbc.gridy++;
        breakSessionPanel.add(gameBtn, gbc);

        // Chat Panel
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea(15, 30);
        chatArea.setEditable(false);
        JTextField inputField = new JTextField();
        JButton sendBtn = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);

        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                chatArea.append("You: " + msg + "\n");
                inputField.setText("");
                chatArea.append("Mate: Stay focused!\n");
            }
        });

        // Game Panel
        GamesPanel gamePanel = new GamesPanel();

        // Add all cards
        rightPanel.add(dashboardPanel, "Dashboard");
        rightPanel.add(pomodoroPanel, "Pomodoro");
        rightPanel.add(breakSessionPanel, "BreakSession");
        rightPanel.add(chatPanel, "Chat");
        rightPanel.add(gamePanel, "Game");
        rightPanel.add(streaksPanel, "Streaks");
        rightPanel.add(todoPanel, "ToDo");
        rightPanel.add(leaderboardPanel, "Leaderboard");

        add(rightPanel, BorderLayout.CENTER);

        // Button Actions
        dashboardBtn.addActionListener(e -> {
            updateWelcomeLabel();
            cardLayout.show(rightPanel, "Dashboard");
        });
        pomodoroBtn.addActionListener(e -> cardLayout.show(rightPanel, "Pomodoro"));
        breakSessionBtn.addActionListener(e -> cardLayout.show(rightPanel, "BreakSession"));
        streaksBtn.addActionListener(e -> cardLayout.show(rightPanel, "Streaks"));
        todoBtn.addActionListener(e -> cardLayout.show(rightPanel, "ToDo"));
        leaderboardBtn.addActionListener(e -> cardLayout.show(rightPanel, "Leaderboard"));
        chatBtn.addActionListener(e -> cardLayout.show(rightPanel, "Chat"));
        gameBtn.addActionListener(e -> cardLayout.show(rightPanel, "Game"));

        signOutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to sign out?",
                    "Confirm Sign Out",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Main.loggedInUser = null;
                clearDashboardState();
                Main.showLoginPanel();
            }
        });
    }

    private void updateWelcomeLabel() {
        if (Main.loggedInUser != null) {
            welcomeLabel.setText("Welcome, " + Main.loggedInUser.firstName + "!");
        } else {
            welcomeLabel.setText("Welcome to FocusMate!");
        }
    }

    private void clearDashboardState() {
        chatArea.setText("");
    }

    public void refreshStreaksPanel() {
        if (streaksPanel != null) {
            streaksPanel.refreshStreak();
        }
    }

    public void refreshLeaderboardPanel() {
        if (leaderboardPanel != null) {
            leaderboardPanel.refreshLeaderboard();
        }
    }
}
