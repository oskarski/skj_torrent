package Host;

import Host.Transport.HostClient;
import Host.Transport.HostTrackerClient;
import Tcp.Response;
import Tcp.TcpServer.TcpServer;

import java.io.IOException;

public class HostState {
    private static boolean isHostRunning;
    private static String workspacePathname;
    private static String hostTrackerIP;
    private static int hostTrackerPort;
    private static TcpServer<Response, HostController> hostTcpServer;
    public static final HostTrackerClient hostTrackerClient = new HostTrackerClient();
    public static final HostClient hostClient = new HostClient();

    public static void init(String workspacePathname, String hostTrackerAddress, TcpServer<Response, HostController> hostTcpServer) {
        HostState.workspacePathname = workspacePathname;
        HostState.hostTrackerIP = hostTrackerAddress.split(":")[0];
        HostState.hostTrackerPort = Integer.parseInt(hostTrackerAddress.split(":")[1]);
        HostState.hostTcpServer = hostTcpServer;
        HostState.isHostRunning = true;
    }

    public static String getWorkspacePathname() {
        return workspacePathname;
    }

    public static String getWorkspaceTmpPathname() {
        return workspacePathname + "/tmp";
    }

    public static int chunkSize() {
        return 256000;
    }

    public static String getHostTrackerAddress() {
        return hostTrackerIP + ":" + hostTrackerPort;
    }

    public static boolean getIsHostRunning() {
        return isHostRunning;
    }

    public static void quitProgram() {
        try {
            HostState.isHostRunning = false;
            HostState.hostTcpServer.stop();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
