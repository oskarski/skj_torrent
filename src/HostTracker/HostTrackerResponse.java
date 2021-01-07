package HostTracker;

import TcpServer.Response;

public class HostTrackerResponse extends Response {
    protected HostTrackerResponse(String method, int code, String data) {
        super(method, code, data);
    }

    public static HostTrackerResponse create(String method, int code, String data) {
        return new HostTrackerResponse(method, code, data);
    }

    public static HostTrackerResponse fromRequest(HostTrackerRequest hostTrackerRequest, String data) {
        return new HostTrackerResponse(hostTrackerRequest.getMethod(), 200, data);
    }

    public static HostTrackerResponse fromRequest(HostTrackerRequest hostTrackerRequest) {
        return HostTrackerResponse.fromRequest(hostTrackerRequest, "");
    }
}
