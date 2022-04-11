package user;

public enum Accuracy {
    TWO (2),
    THREE (3),
    FOUR (4);

    private int accuracy;

    Accuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
