package Host;

import Server.Controller;
import Server.ServerException;
import utils.Regex;

import java.util.regex.Matcher;

public class HostController extends Controller<HostRequest, HostResponse> {
    private final HostService hostService = new HostService();

    @Override
    public HostResponse handleRequest(HostRequest request) {
        this.request = request;

        if (this.request.getMethod().equals(HostMethod.LIST_FILES)) return this.listFiles();
        if (this.request.getMethod().equals(HostMethod.PING)) return this.ping();
        if (this.request.getMethod().equals(HostMethod.PULL_FILE)) return this.pullFile();

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

    private HostResponse pullFile() {
        if (this.request.getData().isEmpty()) throw ServerException.badRequestException();

        Matcher matcher = this.getDataMatcher("^(" + Regex.fileHashRegex() + ") ([0-9]+)$");

        String fileHash = matcher.group(1);
        int chunk = Integer.parseInt(matcher.group(2));

        String data = this.hostService.getPullFileData(fileHash, chunk);

        return HostResponse.fromRequest(this.request, data);
    }
}
