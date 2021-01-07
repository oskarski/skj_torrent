package Host.UI.MenuAction;

import Host.HostState;
import Host.Transport.ListFilesItem;

import java.util.ArrayList;

public class ListHostsFilesMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List hosts files";
    }

    @Override
    public void call() {
        ArrayList<String> hosts = HostState.hostTrackerClient.listHosts();

        for (String hostAddress : hosts) {
            System.out.println(" -> " + hostAddress);

            for (ListFilesItem listFilesItem : HostState.hostClient.listFiles(hostAddress)) {
                System.out.println("    -> " + listFilesItem.fileName + " " + listFilesItem.size + "B");
            }
        }
    }
}
