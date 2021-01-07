package HostTracker;

import TcpServer.Request;
import TcpServer.Response;

public class HostTrackerResponse extends Response {
    protected HostTrackerResponse(String method, int code, String data) {
        super(method, code, data);
    }

    public static HostTrackerResponse create(String method, int code, String data) {
        return new HostTrackerResponse(method, code, data);
    }

    public static HostTrackerResponse fromRequest(Request request, String data) {
        return new HostTrackerResponse(request.getMethod(), 200, data);
    }

    public static HostTrackerResponse fromRequest(Request request) {
        return HostTrackerResponse.fromRequest(request, "");
    }
}
