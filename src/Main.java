import javax.swing.*;
import java.awt.*;

public class Main {

    public static JFrame frame;
    public static Users users = new Users("users.txt");
    public static Users.User loggedInUser;
    public static FocusMateDashboard dashboard;

    private static CardLayout cardLayout = new CardLayout();
    private static JPanel mainPanel = new JPanel(cardLayout);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createGUI);
    }

    private static void createGUI() {
        frame = new JFrame("FocusMate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);

        LoginPanel loginPanel = new LoginPanel();
        SignUpPanel signUpPanel = new SignUpPanel();

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(signUpPanel, "SignUp");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public static void showLoginPanel() {
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof LoginPanel lp) lp.clearFields();
        }
        cardLayout.show(mainPanel, "Login");
    }

    public static void showSignupPanel() {
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof SignUpPanel sp) sp.clearFields();
        }
        cardLayout.show(mainPanel, "SignUp");
    }

    public static void showDashboard() {
        // Remove old dashboard if exists
        for (Component c : mainPanel.getComponents()) {
            if (c instanceof FocusMateDashboard) {
                mainPanel.remove(c);
                break;
            }
        }

        dashboard = new FocusMateDashboard();
        mainPanel.add(dashboard, "Dashboard");
        cardLayout.show(mainPanel, "Dashboard");

        frame.revalidate();
        frame.repaint();
    }
}
