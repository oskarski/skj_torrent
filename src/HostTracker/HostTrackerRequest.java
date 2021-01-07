package HostTracker;

import TcpServer.Request;

public class HostTrackerRequest extends Request {
    public HostTrackerRequest(String method, String data) {
        super(method, data);
    }

    public HostTrackerRequest(String method) {
        super(method, "");
    }
}
