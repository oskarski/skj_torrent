package Server;

public abstract class Request {
    protected final String data;

    protected Request(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
