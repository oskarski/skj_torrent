package Host.MenuAction;

import Host.Client.ListFilesItem;
import Host.HostState;

public class ListPeerFilesMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List peer files";
    }

    @Override
    public void call() {
        for (ListFilesItem listFilesItem : HostState.hostClient.listFiles(HostState.getPeerAddress())) {
            System.out.println("    -> " + listFilesItem.fileName + " " + listFilesItem.size + "B");
        }
    }
}
