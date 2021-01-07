package Tcp.TcpServer;

public class Request {
    protected final String method;
    protected final String data;

    public Request(String method, String data) {
        this.method = method;
        this.data = data;
    }

    public Request(String method) {
        this(method, "");
    }

    public String getData() {
        return data;
    }

    public String getMethod() {
        return method;
    }
}
