import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarToDoPanel extends JPanel {
    private JComboBox<String> dateComboBox;
    private DefaultListModel<String> taskModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton, removeButton, markDoneButton;

    private Map<String, DefaultListModel<String>> tasksByDate;
    private SimpleDateFormat sdf;

    public CalendarToDoPanel() {
        setLayout(new BorderLayout(10, 10));

        tasksByDate = new HashMap<>();
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Date selector (like a simple calendar dropdown)
        JPanel topPanel = new JPanel(new FlowLayout());
        dateComboBox = new JComboBox<>();
        populateDateComboBox();
        topPanel.add(new JLabel("Select Date:"));
        topPanel.add(dateComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Task Panel
        JPanel taskPanel = new JPanel(new BorderLayout(5, 5));
        taskModel = new DefaultListModel<>();
        taskList = new JList<>(taskModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        taskInput = new JTextField();
        addButton = new JButton("Add Task");
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        markDoneButton = new JButton("Mark Done");
        removeButton = new JButton("Remove Task");
        buttonPanel.add(markDoneButton);
        buttonPanel.add(removeButton);

        taskPanel.add(inputPanel, BorderLayout.SOUTH);
        taskPanel.add(buttonPanel, BorderLayout.NORTH);

        add(taskPanel, BorderLayout.CENTER);

        // Event listeners
        dateComboBox.addActionListener(e -> loadTasksForSelectedDate());
        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
        markDoneButton.addActionListener(e -> markTaskDone());

        loadTasksForSelectedDate();
    }

    private void populateDateComboBox() {
        Calendar cal = Calendar.getInstance();
        for (int i = -7; i <= 30; i++) { // past 7 days + next 30 days
            Calendar tempCal = (Calendar) cal.clone();
            tempCal.add(Calendar.DAY_OF_MONTH, i);
            dateComboBox.addItem(sdf.format(tempCal.getTime()));
        }
        dateComboBox.setSelectedIndex(7); // select today by default
    }

    private void loadTasksForSelectedDate() {
        String selectedDate = (String) dateComboBox.getSelectedItem();
        taskModel.clear();
        if (selectedDate != null) {
            DefaultListModel<String> tasks = tasksByDate.get(selectedDate);
            if (tasks != null) {
                for (int i = 0; i < tasks.size(); i++) {
                    taskModel.addElement(tasks.getElementAt(i));
                }
            }
        }
    }

    private void saveTasksForSelectedDate() {
        String selectedDate = (String) dateComboBox.getSelectedItem();
        if (selectedDate != null) {
            DefaultListModel<String> tasks = new DefaultListModel<>();
            for (int i = 0; i < taskModel.size(); i++) {
                tasks.addElement(taskModel.getElementAt(i));
            }
            tasksByDate.put(selectedDate, tasks);
        }
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (!task.isEmpty()) {
            taskModel.addElement(task);
            taskInput.setText("");
            saveTasksForSelectedDate();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task.");
        }
    }

    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskModel.remove(selectedIndex);
            saveTasksForSelectedDate();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to remove.");
        }
    }

    private void markTaskDone() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String task = taskModel.getElementAt(selectedIndex);
            if (!task.startsWith("✔ ")) {
                taskModel.set(selectedIndex, "✔ " + task);
            }
            saveTasksForSelectedDate();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark done.");
        }
    }

    // Test panel
    public static void main(String[] args) {
        JFrame frame = new JFrame("Calendar To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new CalendarToDoPanel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
