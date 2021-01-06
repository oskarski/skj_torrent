package Host;

public class HostState {
    private static boolean isHostRunning;
    private static String workspacePathname;
    private static String hostTrackerIP;
    private static int hostTrackerPort;

    public static void init(String workspacePathname, String hostTrackerAddress) {
        HostState.workspacePathname = workspacePathname;
        HostState.hostTrackerIP = hostTrackerAddress.split(":")[0];
        HostState.hostTrackerPort = Integer.parseInt(hostTrackerAddress.split(":")[1]);
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

    public static void setIsHostRunning(boolean isHostRunning) {
        HostState.isHostRunning = isHostRunning;
    }
}
