package Host;

import Server.Response;

public class HostResponse extends Response {
    protected HostResponse(String method, int code, String data) {
        super(method, code, data);
    }

    public static HostResponse fromRequest(HostRequest hostTrackerRequest, String data) {
        return new HostResponse(hostTrackerRequest.getMethod(), 200, data);
    }

    public static HostResponse fromRequest(HostRequest hostTrackerRequest) {
        return HostResponse.fromRequest(hostTrackerRequest, "");
    }

    public static HostResponse create(String method, int code, String data) {
        return new HostResponse(method, code, data);
    }
}
