import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PomodoroPanel extends JPanel {

    private JTextField minutesField;
    private JLabel timerLabel, statusLabel;
    private JButton startBtn, pauseBtn, resetBtn;
    private Timer timer;
    private int totalSeconds;
    private boolean running = false;
    private boolean paused = false;

    public PomodoroPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 255)); // light pastel background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel enterLabel = new JLabel("Enter minutes:");
        enterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        minutesField = new JTextField(5);
        minutesField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        minutesField.setHorizontalAlignment(JTextField.CENTER);

        timerLabel = new JLabel("00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        timerLabel.setForeground(new Color(70, 130, 180));

        statusLabel = new JLabel("Ready to focus!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        statusLabel.setForeground(new Color(100, 100, 100));

        startBtn = createStyledButton("▶ Start", new Color(76, 175, 80));  // green
        pauseBtn = createStyledButton("⏸ Pause", new Color(255, 193, 7)); // amber
        resetBtn = createStyledButton("⟳ Reset", new Color(244, 67, 54)); // red

        gbc.gridx = 0; gbc.gridy = 0;
        add(enterLabel, gbc);
        gbc.gridx = 1;
        add(minutesField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(timerLabel, gbc);

        gbc.gridy = 2;
        add(statusLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 3;
        add(startBtn, gbc);
        gbc.gridx = 1;
        add(pauseBtn, gbc);
        gbc.gridx = 2;
        add(resetBtn, gbc);

        // ===== Button Actions =====
        startBtn.addActionListener(e -> startOrResumeTimer());
        pauseBtn.addActionListener(e -> pauseTimer());
        resetBtn.addActionListener(e -> resetTimer());
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(color.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        return btn;
    }

    private void startOrResumeTimer() {
        // If already running, ignore
        if (running) return;

        // If paused, just resume the timer
        if (paused && totalSeconds > 0) {
            timer.start();
            running = true;
            paused = false;
            statusLabel.setText("Resumed ⏱️");
            return;
        }

        // Otherwise, start a new session
        try {
            int minutes = Integer.parseInt(minutesField.getText().trim());
            if (minutes <= 0) throw new NumberFormatException();
            totalSeconds = (minutes * 60) - 1; // start from mm:59
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of minutes.",
                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        updateTimerLabel();
        running = true;
        paused = false;
        statusLabel.setText("Focus mode ON 🔥");

        timer = new Timer(1000, e -> {
            if (totalSeconds > 0) {
                totalSeconds--;
                updateTimerLabel();
            } else {
                timer.stop();
                running = false;
                paused = false;
                updateTimerLabel();
                statusLabel.setText("Session complete ✅");
                
                // Increment user streak/score on successful completion
                if (Main.loggedInUser != null) {
                    Main.users.incrementUserScore(Main.loggedInUser.email);
                    // The incrementUserScore method already updates the user object in the list
                    // We need to sync the loggedInUser object with the updated data
                    Main.loggedInUser = Main.users.getUserByEmail(Main.loggedInUser.email);
                    
                    // Refresh both streaks and leaderboard panels to show updated score immediately
                    if (Main.dashboard != null) {
                        Main.dashboard.refreshStreaksPanel();
                        Main.dashboard.refreshLeaderboardPanel();
                    }
                }
                
                JOptionPane.showMessageDialog(PomodoroPanel.this,
                        "🎉 You have successfully completed your Pomodoro session!\nYour streak has been increased!",
                        "Session Complete",
                        JOptionPane.INFORMATION_MESSAGE);
                resetTimer();
            }
        });
        timer.start();
    }

    private void pauseTimer() {
        if (timer != null && running) {
            timer.stop();
            running = false;
            paused = true;
            statusLabel.setText("Paused ⏸");
        }
    }

    private void resetTimer() {
        if (timer != null) timer.stop();
        running = false;
        paused = false;
        totalSeconds = 0;
        minutesField.setText("");
        timerLabel.setText("00:00");
        statusLabel.setText("Ready to focus!");
    }

    private void updateTimerLabel() {
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", mins, secs));
    }

    // For testing standalone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Pomodoro Timer Test");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(400, 300);
            f.setLocationRelativeTo(null);
            f.add(new PomodoroPanel());
            f.setVisible(true);
        });
    }
}