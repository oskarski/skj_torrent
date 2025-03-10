package Host.MenuAction;

import Host.HostState;

import java.util.ArrayList;

public class ListHostsMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List hosts";
    }

    @Override
    public void call() {
        ArrayList<String> hosts = HostState.hostTrackerClient.listHosts();

        for (String host : hosts) {
            if (host.equals(HostState.getHostTcpServer().getAddress())) continue;
            System.out.println(" -> " + host);
        }
    }
}
