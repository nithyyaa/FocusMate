import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GuessNumberPanel extends JPanel {
    private JTextField inputField;
    private JButton guessButton, newGameButton;
    private JLabel infoLabel, attemptsLabel;
    private int targetNumber;
    private int attempts;
    private Random random;

    public GuessNumberPanel() {
        setLayout(new GridLayout(5, 1, 5, 5));
        random = new Random();

        // Labels
        infoLabel = new JLabel("Guess a number between 1 and 100:", SwingConstants.CENTER);
        attemptsLabel = new JLabel("Attempts: 0", SwingConstants.CENTER);

        // Input field and buttons
        inputField = new JTextField();
        guessButton = new JButton("Guess");
        newGameButton = new JButton("New Game");

        // Add components to panel
        add(infoLabel);
        add(inputField);
        add(guessButton);
        add(attemptsLabel);
        add(newGameButton);

        // Button actions
        guessButton.addActionListener(e -> makeGuess());
        newGameButton.addActionListener(e -> startNewGame());

        startNewGame();
    }

    private void startNewGame() {
        targetNumber = random.nextInt(100) + 1; // 1-100
        attempts = 0;
        infoLabel.setText("Guess a number between 1 and 100:");
        attemptsLabel.setText("Attempts: 0");
        inputField.setText("");
    }

    private void makeGuess() {
        try {
            int guess = Integer.parseInt(inputField.getText());
            attempts++;
            attemptsLabel.setText("Attempts: " + attempts);

            if (guess < targetNumber) {
                infoLabel.setText("Too low! Try again:");
            } else if (guess > targetNumber) {
                infoLabel.setText("Too high! Try again:");
            } else {
                infoLabel.setText("Correct! Number was " + targetNumber);
                int option = JOptionPane.showConfirmDialog(this,
                        "You guessed it in " + attempts + " attempts!\nPlay again?",
                        "Victory", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    startNewGame();
                }
            }
            inputField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }

    // Optional: test panel independently
    public static void main(String[] args) {
        JFrame frame = new JFrame("Guess Number Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.add(new GuessNumberPanel());
        frame.setVisible(true);
    }
}
