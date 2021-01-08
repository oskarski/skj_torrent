package HostTracker;


public class HostTrackerService {
    public void registerHost(String hostAddress) {
        HostTrackerState.hosts.add(hostAddress);
    }

    public void unregisterHost(String hostAddress) {
        HostTrackerState.hosts.remove(hostAddress);
    }

    public String getHostsData() {
        StringBuilder data = new StringBuilder();

        for (String host : HostTrackerState.hosts) {
            data.append(host).append("\r\n");
        }

        data.append("\r\n\r\n");

        return data.toString();
    }
}
