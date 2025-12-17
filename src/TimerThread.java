public class TimerThread extends Thread {
    private int minutes;

    public TimerThread(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public void run() {
        try {
            for (int i = minutes; i > 0; i--) {
                System.out.println("Time left: " + i + " minute(s)");
                Thread.sleep(60000); // 60,000 ms = 1 real minute
            }
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted.");
        }
    }
}
