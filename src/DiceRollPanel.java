import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DiceRollPanel extends JPanel {
    private JButton rollButton;
    private JLabel resultLabel;
    private Random random;

    public DiceRollPanel() {
        random = new Random();
        setLayout(new GridLayout(3, 1, 5, 5));

        // Label
        resultLabel = new JLabel("Click Roll to roll the dice", SwingConstants.CENTER);

        // Button
        rollButton = new JButton("Roll Dice");

        // Add components
        add(resultLabel);
        add(rollButton);

        // Action
        rollButton.addActionListener(e -> rollDice());
    }

    private void rollDice() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        resultLabel.setText("You rolled: " + dice1 + " and " + dice2 + " (Total: " + (dice1 + dice2) + ")");
    }

    // Optional standalone test
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dice Roll");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.add(new DiceRollPanel());
        frame.setVisible(true);
    }
}
