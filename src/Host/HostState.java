package Host;

import Host.Client.HostClient;
import Host.Client.HostTrackerClient;
import Host.Server.HostController;
import Tcp.Response;
import Tcp.TcpServer.TcpServer;

import java.io.IOException;

public class HostState {
    private static boolean isHostRunning;
    private static String workspacePathname;
    private static String hostTrackerIP = "";
    private static int hostTrackerPort;
    private static String peerIP;
    private static int peerPort;
    private static boolean h2hMode;
    private static TcpServer<Response, HostController> hostTcpServer;
    public static final HostTrackerClient hostTrackerClient = new HostTrackerClient();
    public static final HostClient hostClient = new HostClient();

    public static void init(String workspacePathname, String hostTrackerOrPeerAddress, TcpServer<Response, HostController> hostTcpServer, boolean h2hMode) {
        HostState.workspacePathname = workspacePathname;
        HostState.hostTcpServer = hostTcpServer;
        HostState.isHostRunning = true;
        HostState.h2hMode = h2hMode;

        if (h2hMode) {
            HostState.peerIP = hostTrackerOrPeerAddress.split(":")[0];
            HostState.peerPort = Integer.parseInt(hostTrackerOrPeerAddress.split(":")[1]);
        } else {
            HostState.hostTrackerIP = hostTrackerOrPeerAddress.split(":")[0];
            HostState.hostTrackerPort = Integer.parseInt(hostTrackerOrPeerAddress.split(":")[1]);
        }
    }

    public static String getWorkspacePathname() {
        return workspacePathname;
    }

    public static String getWorkspacePullTmpPathname() {
        return workspacePathname + "/tmp/pull";
    }

    public static String getWorkspacePushTmpPathname() {
        return workspacePathname + "/tmp/push";
    }

    public static int chunkSize() {
        return 256000;
    }

    public static String getHostTrackerAddress() {
        return hostTrackerIP + ":" + hostTrackerPort;
    }

    public static String getPeerAddress() {
        return peerIP + ":" + peerPort;
    }

    public static boolean getIsHostRunning() {
        return isHostRunning;
    }

    public static TcpServer<Response, HostController> getHostTcpServer() {
        return hostTcpServer;
    }

    public static String getPeerIP() {
        return peerIP;
    }

    public static int getPeerPort() {
        return peerPort;
    }

    public static boolean isH2hMode() {
        return h2hMode;
    }

    public static void quitProgram() {
        try {
            HostState.isHostRunning = false;
            HostState.hostTcpServer.stop();
            if (!h2hMode) hostTrackerClient.unregisterHost(hostTcpServer.getAddress());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
