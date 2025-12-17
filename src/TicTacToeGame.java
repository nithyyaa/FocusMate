import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGame extends JPanel {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel;

    public TicTacToeGame() {
        setLayout(new BorderLayout(5, 5));

        // Status label
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Board panel
        JPanel board = new JPanel(new GridLayout(3, 3, 5, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                buttons[i][j].addActionListener(new ButtonListener(i, j));
                board.add(buttons[i][j]);
            }
        }
        add(board, BorderLayout.CENTER);

        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetBoard());
        add(resetButton, BorderLayout.SOUTH);
    }

    private class ButtonListener implements ActionListener {
        private int row, col;

        public ButtonListener(int r, int c) {
            row = r;
            col = c;
        }

        public void actionPerformed(ActionEvent e) {
            if (buttons[row][col].getText().equals("")) {
                buttons[row][col].setText(String.valueOf(currentPlayer));
                if (checkWin()) {
                    JOptionPane.showMessageDialog(TicTacToeGame.this, "Player " + currentPlayer + " wins!");
                    resetBoard();
                    return;
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(TicTacToeGame.this, "It's a tie!");
                    resetBoard();
                    return;
                }
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().equals("") &&
                buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                buttons[i][1].getText().equals(buttons[i][2].getText())) {
                return true;
            }
            if (!buttons[0][i].getText().equals("") &&
                buttons[0][i].getText().equals(buttons[1][i].getText()) &&
                buttons[1][i].getText().equals(buttons[2][i].getText())) {
                return true;
            }
        }
        // Check diagonals
        if (!buttons[0][0].getText().equals("") &&
            buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText())) {
            return true;
        }
        if (!buttons[0][2].getText().equals("") &&
            buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][0].getText())) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                if (btn.getText().equals("")) return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setText("");
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
    }

    // Test panel independently
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic Tac Toe Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new TicTacToeGame());
        frame.setVisible(true);
    }
}
