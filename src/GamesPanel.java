import javax.swing.*;
import java.awt.*;

public class GamesPanel extends JPanel {
    private JPanel optionsPanel;      // Left buttons (game selection)
    private JPanel gameContentPanel;  // Right content (selected game)

    public GamesPanel() {
        setLayout(new BorderLayout(10, 10));

        // Left options panel
        optionsPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton guessNumberButton = new JButton("Guess Number");
        JButton rpsButton = new JButton("Rock Paper Scissors");
        JButton diceButton = new JButton("Dice Roll");
        JButton memoryButton = new JButton("Memory Game");

        optionsPanel.add(ticTacToeButton);
        optionsPanel.add(guessNumberButton);
        optionsPanel.add(rpsButton);
        optionsPanel.add(diceButton);
        optionsPanel.add(memoryButton);

        // Right content panel
        gameContentPanel = new JPanel(new BorderLayout());

        add(optionsPanel, BorderLayout.WEST);
        add(gameContentPanel, BorderLayout.CENTER);

        // Button actions
        ticTacToeButton.addActionListener(e -> openTicTacToe());
        guessNumberButton.addActionListener(e -> openGuessNumber());
        rpsButton.addActionListener(e -> openRockPaperScissors());
        diceButton.addActionListener(e -> openDiceRoll());
        memoryButton.addActionListener(e -> openMemoryGame());
    }

    private void openTicTacToe() {
        gameContentPanel.removeAll();
        gameContentPanel.add(new TicTacToeGame(), BorderLayout.CENTER);
        gameContentPanel.revalidate();
        gameContentPanel.repaint();
    }

    private void openGuessNumber() {
        gameContentPanel.removeAll();
        gameContentPanel.add(new GuessNumberPanel(), BorderLayout.CENTER);
        gameContentPanel.revalidate();
        gameContentPanel.repaint();
    }

    private void openRockPaperScissors() {
        gameContentPanel.removeAll();
        gameContentPanel.add(new RockPaperScissorsPanel(), BorderLayout.CENTER);
        gameContentPanel.revalidate();
        gameContentPanel.repaint();
    }

    private void openDiceRoll() {
        gameContentPanel.removeAll();
        gameContentPanel.add(new DiceRollPanel(), BorderLayout.CENTER);
        gameContentPanel.revalidate();
        gameContentPanel.repaint();
    }

    private void openMemoryGame() {
        gameContentPanel.removeAll();
        gameContentPanel.add(new MemoryGamePanel(), BorderLayout.CENTER);
        gameContentPanel.revalidate();
        gameContentPanel.repaint();
    }

    // Optional: test independently
    public static void main(String[] args) {
        JFrame frame = new JFrame("Games Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.add(new GamesPanel());
        frame.setVisible(true);
    }
}
