import java.io.*;
import java.util.*;

public class FileManager {
    private File file;

    public FileManager(String filename) {
        file = new File(filename);
    }

    public List<String> readAllLines() {
        List<String> lines = new ArrayList<>();
        try {
            if (!file.exists()) file.createNewFile();
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) lines.add(sc.nextLine());
            }
        } catch (IOException e) { e.printStackTrace(); }
        return lines;
    }

    public void appendLine(String line) {
        try {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
                pw.println(line);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void overwriteAll(List<String> lines) {
        try {
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                for (String line : lines) pw.println(line);
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
