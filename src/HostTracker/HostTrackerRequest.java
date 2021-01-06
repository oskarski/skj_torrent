package HostTracker;

import Server.Request;

public class HostTrackerRequest extends Request {
    private final String method;

    public HostTrackerRequest(String method, String data) {
        super(data);

        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getData() {
        return data;
    }
}
