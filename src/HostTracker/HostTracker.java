package HostTracker;

import utils.Regex;
import utils.transport.ServerException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostTracker {
    private final HostTrackerRequest hostTrackerRequest;
    private final HostTrackerService hostTrackerService = new HostTrackerService();

    private HostTracker(HostTrackerRequest hostTrackerRequest) {
        this.hostTrackerRequest = hostTrackerRequest;
    }

    public static HostTrackerResponse fromTrackerRequest(HostTrackerRequest hostTrackerRequest) {
        HostTracker self = new HostTracker(hostTrackerRequest);

        if (hostTrackerRequest.getMethod().equals(HostTrackerMethod.LIST_HOSTS)) return self.listHosts();
        if (hostTrackerRequest.getMethod().equals(HostTrackerMethod.REGISTER_HOST)) return self.registerHost();
        if (hostTrackerRequest.getMethod().equals(HostTrackerMethod.UNREGISTER_HOST)) return self.unregisterHost();

        return null;
    }

    private HostTrackerResponse listHosts() {
        if (!this.hostTrackerRequest.getData().isEmpty()) throw ServerException.badRequestException();

        String data = this.hostTrackerService.getHostsData();

        return HostTrackerResponse.fromRequest(this.hostTrackerRequest, data);
    }

    private HostTrackerResponse registerHost() {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.registerHost(address);

        return HostTrackerResponse.fromRequest(this.hostTrackerRequest);
    }

    private HostTrackerResponse unregisterHost() {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.unregisterHost(address);

        return HostTrackerResponse.fromRequest(this.hostTrackerRequest);
    }

    private Matcher getDataMatcher(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.hostTrackerRequest.getData());

        if (!matcher.find()) throw ServerException.badRequestException();

        return matcher;
    }
}
