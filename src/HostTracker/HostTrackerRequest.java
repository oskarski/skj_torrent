package HostTracker;

import Server.Request;

public class HostTrackerRequest implements Request {
    private final String method;
    private final String data;

    public HostTrackerRequest(String method, String data) {
        this.method = method;
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public String getData() {
        return data;
    }
}
