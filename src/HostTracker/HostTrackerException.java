package HostTracker;

public class HostTrackerException extends Exception {
    private int code;

    private HostTrackerException(String message, int code) {
        super(message);

        this.code = code;
    }

    public static HostTrackerException badRequestException() {
        return new HostTrackerException("Bad request", 400);
    }

    public static HostTrackerException internalServerErrorException() {
        return new HostTrackerException("Internal server error", 500);
    }

    public int getCode() {
        return this.code;
    }
}
