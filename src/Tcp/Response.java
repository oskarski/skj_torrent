package Tcp;

public class Response {
    protected final String method;
    protected final int code;
    protected final String data;

    public Response(String method, int code, String data) {
        this.method = method;
        this.code = code;
        this.data = data;
    }

    public Response(String method, String data) {
        this(method, 200, data);
    }

    public Response(String method) {
        this(method, 200, "");
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
