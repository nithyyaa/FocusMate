import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Users {

    private List<User> userList = new ArrayList<>();
    private String filePath;

    public Users(String filePath) {
        this.filePath = filePath;
        loadUsers();
    }

    public static class User {
        public String firstName, lastName, email, password;
        public int score;

        public User(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.score = 0; // Initialize score to 0
        }
    }

    public void addUser(User user) {
        userList.add(user);
        saveUsers();
    }

    public boolean userExists(String email) {
        return getUserByEmail(email) != null;
    }

    public User getUserByEmail(String email) {
        for (User u : userList) {
            if (u.email.equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }

    public void updateUserScore(String email, int newScore) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.score = newScore;
            saveUsers();
        }
    }

    public void incrementUserScore(String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.score++;
            saveUsers();
        }
    }

    private void loadUsers() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    userList.add(new User(parts[0], parts[1], parts[2], parts[3]));
                } else if (parts.length == 5) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3]);
                    user.score = Integer.parseInt(parts[4]);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User u : userList) {
                bw.write(u.firstName + "," + u.lastName + "," + u.email + "," + u.password + "," + u.score);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
