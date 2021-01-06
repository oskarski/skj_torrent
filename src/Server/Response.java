package Server;

public abstract class Response {
    protected final String method;
    protected final int code;
    protected final String data;

    protected Response(String method, int code, String data) {
        this.method = method;
        this.code = code;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getMethod() {
        return method;
    }

    public int getCode() {
        return code;
    }
}
