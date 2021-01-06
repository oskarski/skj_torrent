package Server;

public class ServerException extends RuntimeException {
    private int code;

    private ServerException(String message, int code) {
        super(message);

        this.code = code;
    }

    public static ServerException badRequestException() {
        return new ServerException("Bad request", 400);
    }

    public static ServerException internalServerErrorException() {
        return new ServerException("Internal server error", 500);
    }

    public int getCode() {
        return this.code;
    }
}
