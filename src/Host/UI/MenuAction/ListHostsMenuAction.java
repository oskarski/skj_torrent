package Host.UI.MenuAction;

import Host.Transport.HostTrackerClient;

import java.util.ArrayList;

public class ListHostsMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List hosts";
    }

    @Override
    public void call() {
        ArrayList<String> hosts = HostTrackerClient.listHosts();

        for (String host : hosts) {
            System.out.println(" -> " + host);
        }
    }
}
