import Host.HostController;
import Host.HostRequestReader;
import Host.HostResponseWriter;
import Host.HostState;
import Host.UI.HostUIThread;
import Server.ServerRequestThread;

import java.net.ServerSocket;
import java.net.Socket;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];
        String hostTrackerAddress = args[2];
        boolean runUI = Boolean.parseBoolean(args[3]);

        try {
            ServerSocket hostServerSocket = new ServerSocket(hostPort);
            HostState.init(workspacePathname, hostTrackerAddress, hostServerSocket);

//            TODO REGISTER IN TRACKER

            if (runUI) new Thread(HostUIThread.create()).start();

            while (HostState.getIsHostRunning()) {
                Socket client = hostServerSocket.accept();

                new Thread(() -> ServerRequestThread.fromClientSocket(
                        client,
                        new HostRequestReader(),
                        new HostResponseWriter(),
                        new HostController()
                )).start();
            }

            hostServerSocket.close();
        } catch (Exception exception) {
//            TODO UNREGISTER IN TRACKER
        }
    }
}
