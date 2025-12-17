import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendBtn;
    private String modelName = "llama2";
    private boolean greeted = false;

    public ChatPanel() {
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendBtn = new JButton("Send");

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
        addHierarchyListener(ev -> {
            if ((ev.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing() && !greeted) {
                greeted = true;
                sendToOllama("Greet the user briefly and ask how you can help.");
            }
        });
    }

    private void sendToOllama(String prompt) {
        new Thread(() -> {
            try {
                URL url = URI.create("http://localhost:11434/api/generate").toURL();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(60000);

                String body = "{" +
                        "\"model\":\"" + modelName + "\"," +
                        "\"prompt\":\"" + escapeJson(prompt) + "\"," +
                        "\"stream\":false" +
                        "}";

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(body.getBytes("UTF-8"));
                }

                int status = conn.getResponseCode();
                StringBuilder sb = new StringBuilder();
                if (status == HttpURLConnection.HTTP_OK) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    }

                    String json = sb.toString();
                    String ai = extractResponseField(json);
                    if (ai == null) ai = "(no response)";
                    final String reply = ai;
                    SwingUtilities.invokeLater(() -> chatArea.append("AI: " + reply + "\n"));
                } else {
                    InputStream es = conn.getErrorStream();
                    if (es != null) {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(es, "UTF-8"))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                            }
                        }
                    }
                    final String errText = sb.length() > 0 ? sb.toString() : ("HTTP " + status);
                    SwingUtilities.invokeLater(() -> chatArea.append("AI: [Error from Ollama] " + errText + "\n"));
                }
            } catch (IOException ex) {
                SwingUtilities.invokeLater(() -> chatArea.append("AI: [Cannot reach Ollama at http://localhost:11434]" + "\n"));
            }
        }).start();
    }

    private static String escapeJson(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int)c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    private static String extractResponseField(String json) {
        String key = "\"response\":";
        int idx = json.indexOf(key);
        if (idx == -1) return null;
        int start = json.indexOf('"', idx + key.length());
        if (start == -1) return null;
        int end = start + 1;
        StringBuilder sb = new StringBuilder();
        boolean escaping = false;
        while (end < json.length()) {
            char ch = json.charAt(end);
            if (escaping) {
                switch (ch) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    case 'u':
                        if (end + 4 < json.length()) {
                            String hex = json.substring(end + 1, end + 5);
                            try {
                                sb.append((char) Integer.parseInt(hex, 16));
                                end += 4;
                            } catch (NumberFormatException ignore) { }
                        }
                        break;
                    default: sb.append(ch); break;
                }
                escaping = false;
            } else if (ch == '\\') {
                escaping = true;
            } else if (ch == '"') {
                break;
            } else {
                sb.append(ch);
            }
            end++;
        }
        return sb.toString();
    }

    private void sendMessage() {
        String msg = inputField.getText();
        if (!msg.isEmpty()) {
            chatArea.append("You: " + msg + "\n");
            inputField.setText("");
            sendToOllama(msg);
        }
    }

    // Call this when the chat window is closing
    public void closeConnection() {
        
    }
}
