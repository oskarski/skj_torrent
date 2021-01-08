import Host.HostState;
import Host.HostUIThread;
import Host.Server.HostController;
import Host.Server.HostMethod;
import Tcp.RequestReader;
import Tcp.Response;
import Tcp.ResponseWriter;
import Tcp.TcpServer.TcpServer;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];
        String hostTrackerAddress = args[2];
        boolean runUI = Boolean.parseBoolean(args[3]);
        boolean h2hMode = args.length >= 5 && Boolean.parseBoolean(args[4]);

        try {
            TcpServer<Response, HostController> server = new TcpServer<Response, HostController>(hostPort)
                    .useRequestReader(new RequestReader(HostMethod::isValidMethod))
                    .useResponseWriter(new ResponseWriter())
                    .useController(new HostController());

            HostState.init(workspacePathname, hostTrackerAddress, server, h2hMode);

            if (!h2hMode) HostState.hostTrackerClient.registerHost(server.getAddress());

            if (runUI) new Thread(HostUIThread.create()).start();

            server.start();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
