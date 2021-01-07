package Host.Transport;

import Client.Client;
import Host.HostMethod;
import Host.HostRequest;
import Host.HostState;
import HostTracker.HostTrackerMethod;
import HostTracker.HostTrackerRequest;
import HostTracker.HostTrackerResponse;

import java.util.ArrayList;
import java.util.Arrays;

public class HostTrackerClient {
    private final Client<HostTrackerRequest, HostTrackerResponse> client;

    public HostTrackerClient() {
        this.client = new Client<HostTrackerRequest, HostTrackerResponse>(new HostTrackerRequestWriter(), new HostTrackerResponseReader());
    }

    public ArrayList<String> listHosts() {
        HostTrackerResponse response = client.call(HostState.getHostTrackerAddress(), new HostTrackerRequest(HostTrackerMethod.LIST_HOSTS));

        return new ArrayList<>(Arrays.asList(response.getData().split("\r\n")));
    }
}
