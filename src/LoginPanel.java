import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        gbc.gridx = 0; gbc.gridy = 0; add(emailLabel, gbc);
        gbc.gridx = 1; add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(passwordLabel, gbc);
        gbc.gridx = 1; add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(loginButton, gbc);
        gbc.gridx = 1; add(signupButton, gbc);

        loginButton.addActionListener(e -> login());
        signupButton.addActionListener(e -> Main.showSignupPanel());
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        Users.User user = Main.users.getUserByEmail(email);
        if (user != null && user.password.equals(password)) {
            Main.loggedInUser = user;
            Main.showDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!");
        }
    }

    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
    }
}
