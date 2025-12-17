import java.util.Random;

public class PomodoroSession {
    private static final String[] QUOTES = {
            "Stay focused and never give up!",
            "Small steps lead to big results.",
            "Discipline is the bridge between goals and success.",
            "Progress, not perfection.",
            "Your future self will thank you!",
            "Every minute counts — make it matter!"
    };

    public static String randomQuote() {
        return QUOTES[new Random().nextInt(QUOTES.length)];
    }
}
