package user;

public enum Notification {
    NINE  (9, 0),
    TEN  (10, 0),
    ELEVEN  (11, 0),
    TWELVE  (12, 0),
    THIRTEEN  (13, 0),
    FOURTEEN  (14, 0),
    FIFTEEN  (15, 0),
    SIXTEEN  (16, 0),
    SEVENTEEN  (17, 0),
    EIGHTEEN  (19, 0),
    SHUTDOWN  (0, 0);

    private int hour;
    private int minute;

    Notification(int hour, int minute)
    {
        this.hour = hour;
        this.minute = minute;
    }
}
