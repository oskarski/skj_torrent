package Tracker;

public class TrackerException extends Exception {
    private int code;

    private TrackerException(String message, int code) {
        super(message);

        this.code = code;
    }

    public static TrackerException badRequestException() {
        return new TrackerException("Bad request", 400);
    }

    public int getCode() {
        return this.code;
    }
}
