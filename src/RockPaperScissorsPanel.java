import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RockPaperScissorsPanel extends JPanel {
    private JButton rockButton, paperButton, scissorsButton;
    private JLabel resultLabel, scoreLabel;
    private int wins = 0, losses = 0, ties = 0;
    private Random random;

    public RockPaperScissorsPanel() {
        random = new Random();
        setLayout(new GridLayout(5, 1, 5, 5));

        // Buttons
        rockButton = new JButton("Rock");
        paperButton = new JButton("Paper");
        scissorsButton = new JButton("Scissors");

        // Labels
        resultLabel = new JLabel("Choose Rock, Paper, or Scissors", SwingConstants.CENTER);
        scoreLabel = new JLabel("Wins: 0 | Losses: 0 | Ties: 0", SwingConstants.CENTER);

        // Add components
        add(resultLabel);
        add(rockButton);
        add(paperButton);
        add(scissorsButton);
        add(scoreLabel);

        // Button actions
        rockButton.addActionListener(e -> play("Rock"));
        paperButton.addActionListener(e -> play("Paper"));
        scissorsButton.addActionListener(e -> play("Scissors"));
    }

    private void play(String userChoice) {
        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = choices[random.nextInt(3)];

        if (userChoice.equals(computerChoice)) {
            resultLabel.setText("Tie! Computer chose " + computerChoice);
            ties++;
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                   (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                   (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            resultLabel.setText("You Win! Computer chose " + computerChoice);
            wins++;
        } else {
            resultLabel.setText("You Lose! Computer chose " + computerChoice);
            losses++;
        }

        scoreLabel.setText("Wins: " + wins + " | Losses: " + losses + " | Ties: " + ties);
    }

    // Optional standalone test
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rock Paper Scissors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.add(new RockPaperScissorsPanel());
        frame.setVisible(true);
    }
}
