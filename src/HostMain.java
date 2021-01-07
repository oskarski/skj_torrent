import Host.HostController;
import Host.HostRequestReader;
import Host.HostState;
import Host.UI.HostUIThread;
import TcpServer.Response;
import TcpServer.ResponseWriter;
import TcpServer.TcpServer;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];
        String hostTrackerAddress = args[2];
        boolean runUI = Boolean.parseBoolean(args[3]);

        try {
            TcpServer<Response, HostController> server = new TcpServer<Response, HostController>(hostPort)
                    .useRequestReader(new HostRequestReader())
                    .useResponseWriter(new ResponseWriter())
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
