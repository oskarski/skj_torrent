import Host.HostState;
import Host.UI.HostUIThread;

import java.net.ServerSocket;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];
        String hostTrackerAddress = args[2];

        try {
            ServerSocket hostServerSocket = new ServerSocket(hostPort);
            HostState.init(workspacePathname, hostTrackerAddress, hostServerSocket);

//            TODO REGISTER IN TRACKER

            new Thread(HostUIThread.create()).start();

//            TODO LISTEN ON INCOMING CONNECTIONS no hostPort
        } catch (Exception exception) {
//            TODO UNREGISTER IN TRACKER
        }
    }
}
