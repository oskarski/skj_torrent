package Host.Transport;

import Client.Client;
import Host.HostState;
import HostTracker.HostTrackerMethod;
import TcpServer.Request;
import TcpServer.Response;

import java.util.ArrayList;
import java.util.Arrays;

public class HostTrackerClient {
    private final Client<Request, Response> client;

    public HostTrackerClient() {
        this.client = new Client<Request, Response>(new HostTrackerRequestWriter(), new HostTrackerResponseReader());
    }

    public ArrayList<String> listHosts() {
        Response response = client.call(HostState.getHostTrackerAddress(), new Request(HostTrackerMethod.LIST_HOSTS));

        return new ArrayList<>(Arrays.asList(response.getData().split("\r\n")));
    }
}
