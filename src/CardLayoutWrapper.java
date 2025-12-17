import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardLayoutWrapper extends JPanel {
    private CardLayout cardLayout;
    private Map<String, JPanel> panels;

    public CardLayoutWrapper() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        panels = new HashMap<>();
    }

    public void addPanel(String name, JPanel panel) {
        panels.put(name, panel);
        add(panel, name);
    }

    public void showPanel(String name) {
        if (panels.containsKey(name)) {
            cardLayout.show(this, name);
        }
    }
}
