package Host.Client;

import Host.HostState;
import HostTracker.HostTrackerMethod;
import Tcp.*;

import java.util.ArrayList;
import java.util.Arrays;

public class HostTrackerClient {
    private final TcpClient tcpClient;

    public HostTrackerClient() {
        this.tcpClient = new TcpClient(new RequestWriter(), new ResponseReader());
    }

    public ArrayList<String> listHosts() {
        Response response = tcpClient.call(HostState.getHostTrackerAddress(), new Request(HostTrackerMethod.LIST_HOSTS));

        return new ArrayList<>(Arrays.asList(response.getData().split("\r\n")));
    }

    public void registerHost(String hostAddress) {
        String data = hostAddress + "\r\n";
        Response response = tcpClient.call(HostState.getHostTrackerAddress(), new Request(HostTrackerMethod.REGISTER_HOST, data));
    }

    public void unregisterHost(String hostAddress) {
        String data = hostAddress + "\r\n";
        Response response = tcpClient.call(HostState.getHostTrackerAddress(), new Request(HostTrackerMethod.UNREGISTER_HOST, data));
    }
}
