import Host.HostController;
import Host.HostRequestReader;
import Host.HostResponse;
import Host.HostState;
import Host.UI.HostUIThread;
import TcpServer.ResponseWriter;
import TcpServer.TcpServer;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];
        String hostTrackerAddress = args[2];
        boolean runUI = Boolean.parseBoolean(args[3]);

        try {
            TcpServer<HostResponse, HostController> server = new TcpServer<HostResponse, HostController>(hostPort)
                    .useRequestReader(new HostRequestReader())
                    .useResponseWriter(new ResponseWriter<HostResponse>())
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
