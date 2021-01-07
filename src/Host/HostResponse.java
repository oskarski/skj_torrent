package Host;

import TcpServer.Request;
import TcpServer.Response;

public class HostResponse extends Response {
    protected HostResponse(String method, int code, String data) {
        super(method, code, data);
    }

    public static HostResponse fromRequest(Request request, String data) {
        return new HostResponse(request.getMethod(), 200, data);
    }

    public static HostResponse fromRequest(Request request) {
        return HostResponse.fromRequest(request, "");
    }

    public static HostResponse create(String method, int code, String data) {
        return new HostResponse(method, code, data);
    }
}
