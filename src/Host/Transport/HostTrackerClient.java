package Host.Transport;

import Client.Client;
import Host.HostState;
import HostTracker.HostTrackerMethod;
import HostTracker.HostTrackerResponse;
import TcpServer.Request;

import java.util.ArrayList;
import java.util.Arrays;

public class HostTrackerClient {
    private final Client<Request, HostTrackerResponse> client;

    public HostTrackerClient() {
        this.client = new Client<Request, HostTrackerResponse>(new HostTrackerRequestWriter(), new HostTrackerResponseReader());
    }

    public ArrayList<String> listHosts() {
        HostTrackerResponse response = client.call(HostState.getHostTrackerAddress(), new Request(HostTrackerMethod.LIST_HOSTS));

        return new ArrayList<>(Arrays.asList(response.getData().split("\r\n")));
    }
}
