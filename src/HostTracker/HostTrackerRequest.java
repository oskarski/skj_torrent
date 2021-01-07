package HostTracker;

import Server.Request;

public class HostTrackerRequest extends Request {
    public HostTrackerRequest(String method, String data) {
        super(method, data);
    }

    public HostTrackerRequest(String method) {
        super(method, "");
    }
}
