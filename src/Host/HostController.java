package Host;

import Server.Controller;
import Server.ServerException;

public class HostController extends Controller<HostRequest, HostResponse> {
    private final HostService hostService = new HostService();

    @Override
    public HostResponse handleRequest(HostRequest request) {
        this.request = request;

        if (this.request.getMethod().equals(HostMethod.LIST_FILES)) return this.listFiles();
        if (this.request.getMethod().equals(HostMethod.PING)) return this.ping();

        return null;
    }

    private HostResponse listFiles() {
        if (!this.request.getData().isEmpty()) throw ServerException.badRequestException();

        String data = this.hostService.getListFilesData();

        return HostResponse.fromRequest(this.request, data);
    }

    private HostResponse ping() {
        return HostResponse.fromRequest(this.request, "");
    }
}
