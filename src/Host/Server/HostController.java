package Host.Server;

import Tcp.Request;
import Tcp.Response;
import Tcp.TcpServer.Controller;
import Tcp.TcpServer.ServerException;
import utils.Regex;

import java.util.regex.Matcher;

public class HostController extends Controller<Request, Response> {
    private final HostService hostService = new HostService();

    @Override
    public Response handleRequest(Request request) {
        this.request = request;

        if (this.request.getMethod().equals(HostMethod.LIST_FILES)) return this.listFiles();
        if (this.request.getMethod().equals(HostMethod.PING)) return this.ping();
        if (this.request.getMethod().equals(HostMethod.PULL_FILE)) return this.pullFile();

        return null;
    }

    private Response listFiles() {
        if (!this.request.getData().isEmpty()) throw ServerException.badRequestException();

        String data = this.hostService.getListFilesData();

        return new Response(this.request.getMethod(), data);
    }

    private Response ping() {
        return new Response(this.request.getMethod(), "");
    }

    private Response pullFile() {
        if (this.request.getData().isEmpty()) throw ServerException.badRequestException();

        Matcher matcher = this.getDataMatcher("^(" + Regex.fileHashRegex() + ") ([0-9]+)$");

        String fileHash = matcher.group(1);
        int chunk = Integer.parseInt(matcher.group(2));

        String data = this.hostService.getPullFileData(fileHash, chunk);

        return new Response(this.request.getMethod(), data);
    }
}
