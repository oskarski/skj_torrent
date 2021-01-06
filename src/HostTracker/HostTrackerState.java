package HostTracker;

public class HostTrackerState {
    private static String hostsPathname;

    public static void initialize(String hostsPathname) {
        HostTrackerState.hostsPathname = hostsPathname;
    }

    public static String getHostsPathname() {
        return hostsPathname;
    }
}
