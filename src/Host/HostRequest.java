package Host;

import Server.Request;

public class HostRequest extends Request {
    private final String method;

    public HostRequest(String method, String data) {
        super(data);

        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
