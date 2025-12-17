import java.io.*;
import java.util.*;

public class Leaderboard {
    private File usersFile = new File("users.txt");

    public Leaderboard() {
        try {
            if (!usersFile.exists()) usersFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------- Get all users ----------------
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Scanner sc = new Scanner(usersFile)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length >= 3) {
                    String name = parts[0];
                    String email = parts[1];
                    int streak = Integer.parseInt(parts[2]);
                    users.add(new User(name, email, streak));
                }
            }
        } catch (FileNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }
        return users;
    }

    // ---------------- Sort users by streak descending ----------------
    public List<User> getSortedUsers() {
        List<User> users = getUsers();
        users.sort((a, b) -> Integer.compare(b.streak, a.streak));
        return users;
    }

    // ---------------- Optional: Update streak ----------------
    public void updateStreak(String email, int newStreak) {
        List<User> users = getUsers();
        try (PrintWriter pw = new PrintWriter(new FileWriter(usersFile))) {
            for (User u : users) {
                if (u.email.equalsIgnoreCase(email)) u.streak = newStreak;
                pw.println(u.name + "," + u.email + "," + u.streak);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------- User class ----------------
    public static class User {
        public String name;
        public String email;
        public int streak;

        public User(String name, String email, int streak) {
            this.name = name;
            this.email = email;
            this.streak = streak;
        }
    }
}
