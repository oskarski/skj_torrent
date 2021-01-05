package Tracker;

public class BadRequestException extends Exception {
    public final int code = 400;

    public BadRequestException() {
        super("Bad request");
    }
}
