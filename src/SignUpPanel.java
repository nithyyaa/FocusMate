import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends JPanel {
    private JTextField firstNameField, lastNameField, emailField;
    private JPasswordField passwordField;

    public SignUpPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        emailField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");

        gbc.gridx = 0; gbc.gridy = 0; add(firstNameLabel, gbc);
        gbc.gridx = 1; add(firstNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(lastNameLabel, gbc);
        gbc.gridx = 1; add(lastNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(emailLabel, gbc);
        gbc.gridx = 1; add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(passwordLabel, gbc);
        gbc.gridx = 1; add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; add(signupButton, gbc);
        gbc.gridx = 1; add(backButton, gbc);

        signupButton.addActionListener(e -> signup());
        backButton.addActionListener(e -> Main.showLoginPanel());
    }

    private void signup() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if (Main.users.userExists(email)) {
            JOptionPane.showMessageDialog(this, "User already exists!");
            return;
        }

        Main.users.addUser(new Users.User(firstName, lastName, email, password));
        JOptionPane.showMessageDialog(this, "Signup successful! You can login now.");
        Main.showLoginPanel();
    }

    public void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }
}
