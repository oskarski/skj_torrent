package Host;

import java.io.IOException;
import java.net.ServerSocket;

public class HostState {
    private static boolean isHostRunning;
    private static String workspacePathname;
    private static String hostTrackerIP;
    private static int hostTrackerPort;
    private static ServerSocket hostServerSocket;

    public static void init(String workspacePathname, String hostTrackerAddress, ServerSocket hostServerSocket) {
        HostState.workspacePathname = workspacePathname;
        HostState.hostTrackerIP = hostTrackerAddress.split(":")[0];
        HostState.hostTrackerPort = Integer.parseInt(hostTrackerAddress.split(":")[1]);
        HostState.hostServerSocket = hostServerSocket;
        HostState.isHostRunning = true;
    }

    public static String getWorkspacePathname() {
        return workspacePathname;
    }

    public static String getHostTrackerIP() {
        return hostTrackerIP;
    }

    public static int getHostTrackerPort() {
        return hostTrackerPort;
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
            HostState.hostServerSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
