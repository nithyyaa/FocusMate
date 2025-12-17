import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MemoryGamePanel extends JPanel {
    private JButton[] buttons;
    private String[] values;
    private JButton firstSelected = null, secondSelected = null;
    private int matchesFound = 0;

    public MemoryGamePanel() {
        setLayout(new GridLayout(4, 4, 5, 5));

        buttons = new JButton[16];
        values = new String[16];

        // Prepare shuffled pairs
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            temp.add("" + i);
            temp.add("" + i);
        }
        Collections.shuffle(temp);
        values = temp.toArray(new String[0]);

        // Create buttons
        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 24));
            buttons[i].addActionListener(new ButtonListener(i));
            add(buttons[i]);
        }
    }

    // Inner class for button click
    private class ButtonListener implements ActionListener {
        private int index;

        public ButtonListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (firstSelected != null && secondSelected != null) return;

            JButton btn = buttons[index];
            btn.setText(values[index]);
            btn.setEnabled(false);

            if (firstSelected == null) {
                firstSelected = btn;
            } else {
                secondSelected = btn;

                // Use fully qualified javax.swing.Timer to avoid conflicts
                javax.swing.Timer t = new javax.swing.Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ev) {
                        checkMatch();
                    }
                });
                t.setRepeats(false);
                t.start();
            }
        }
    }

    // Check if the selected buttons match
    private void checkMatch() {
        if (!firstSelected.getText().equals(secondSelected.getText())) {
            firstSelected.setText("");
            secondSelected.setText("");
            firstSelected.setEnabled(true);
            secondSelected.setEnabled(true);
        } else {
            matchesFound++;
            if (matchesFound == 8) {
                JOptionPane.showMessageDialog(this, "🎉 You matched all pairs!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        firstSelected = null;
        secondSelected = null;
    }

    // Optional standalone test
    public static void main(String[] args) {
        JFrame frame = new JFrame("Memory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new MemoryGamePanel());
        frame.setVisible(true);
    }
}
