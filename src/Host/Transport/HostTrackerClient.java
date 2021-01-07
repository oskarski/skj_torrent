package Host.Transport;

import Host.HostState;
import HostTracker.HostTrackerMethod;
import TcpClient.TcpClient;
import TcpServer.Request;
import TcpServer.Response;

import java.util.ArrayList;
import java.util.Arrays;

public class HostTrackerClient {
    private final TcpClient<Request, Response> tcpClient;

    public HostTrackerClient() {
        this.tcpClient = new TcpClient<Request, Response>(new HostTrackerRequestWriter(), new HostTrackerResponseReader());
    }

    public ArrayList<String> listHosts() {
        Response response = tcpClient.call(HostState.getHostTrackerAddress(), new Request(HostTrackerMethod.LIST_HOSTS));

        return new ArrayList<>(Arrays.asList(response.getData().split("\r\n")));
    }
}
