package HostTracker;

import utils.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostTracker {
    private final HostTrackerRequest hostTrackerRequest;
    private final HostTrackerService hostTrackerService = new HostTrackerService();

    private HostTracker(HostTrackerRequest hostTrackerRequest) {
        this.hostTrackerRequest = hostTrackerRequest;
    }

    public static HostTrackerResponse fromTrackerRequest(HostTrackerRequest hostTrackerRequest) throws HostTrackerException {
        HostTracker self = new HostTracker(hostTrackerRequest);

        if (hostTrackerRequest.getMethod().equals(HostTrackerMethod.LIST_HOSTS)) return self.listHosts();
        if (hostTrackerRequest.getMethod().equals(HostTrackerMethod.REGISTER_HOST)) return self.registerHost();
        if (hostTrackerRequest.getMethod().equals(HostTrackerMethod.UNREGISTER_HOST)) return self.unregisterHost();

        return null;
    }

    private HostTrackerResponse listHosts() throws HostTrackerException {
        if (!this.hostTrackerRequest.getData().isEmpty()) throw HostTrackerException.badRequestException();

        String data = this.hostTrackerService.getHostsData();

        return HostTrackerResponse.fromRequest(this.hostTrackerRequest, data);
    }

    private HostTrackerResponse registerHost() throws HostTrackerException {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.registerHost(address);

        return HostTrackerResponse.fromRequest(this.hostTrackerRequest);
    }

    private HostTrackerResponse unregisterHost() throws HostTrackerException {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.unregisterHost(address);

        return HostTrackerResponse.fromRequest(this.hostTrackerRequest);
    }

    private Matcher getDataMatcher(String regex) throws HostTrackerException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.hostTrackerRequest.getData());

        if (!matcher.find()) throw HostTrackerException.badRequestException();

        return matcher;
    }
}
