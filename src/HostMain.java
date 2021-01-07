import Host.*;
import Host.UI.HostUIThread;
import TcpServer.ServerRequestThread;
import TcpServer.TcpServer;

import java.net.ServerSocket;
import java.net.Socket;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];
        String hostTrackerAddress = args[2];
        boolean runUI = Boolean.parseBoolean(args[3]);

        try {
            TcpServer<HostRequest, HostResponse, HostController> server = new TcpServer<HostRequest, HostResponse, HostController>(hostPort)
                    .useRequestReader(new HostRequestReader())
                    .useResponseWriter(new HostResponseWriter())
                    .useController(new HostController());

            HostState.init(workspacePathname, hostTrackerAddress, server);

//            TODO REGISTER IN TRACKER

            if (runUI) new Thread(HostUIThread.create()).start();

            server.start();
        } catch (Exception exception) {
//            TODO UNREGISTER IN TRACKER
        }
    }
}
