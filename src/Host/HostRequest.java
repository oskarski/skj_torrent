package Host;

import Server.Request;

public class HostRequest extends Request {
    public HostRequest(String method, String data) {
        super(method, data);
    }

    public HostRequest(String method) {
        super(method, "");
    }
}
