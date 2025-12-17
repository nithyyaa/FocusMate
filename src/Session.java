public abstract class Session {
    private int minutes;

    public Session(int minutes) { this.minutes = minutes; }
    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) { this.minutes = minutes; }
    public abstract void onSessionComplete();
}
