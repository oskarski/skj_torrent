package HostTracker;

import TcpServer.Request;
import TcpServer.ServerException;
import utils.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostTracker {
    private final Request request;
    private final HostTrackerService hostTrackerService = new HostTrackerService();

    private HostTracker(Request request) {
        this.request = request;
    }

    public static HostTrackerResponse fromTrackerRequest(Request request) {
        HostTracker self = new HostTracker(request);

        if (request.getMethod().equals(HostTrackerMethod.LIST_HOSTS)) return self.listHosts();
        if (request.getMethod().equals(HostTrackerMethod.REGISTER_HOST)) return self.registerHost();
        if (request.getMethod().equals(HostTrackerMethod.UNREGISTER_HOST)) return self.unregisterHost();

        return null;
    }

    private HostTrackerResponse listHosts() {
        if (!this.request.getData().isEmpty()) throw ServerException.badRequestException();

        String data = this.hostTrackerService.getHostsData();

        return HostTrackerResponse.fromRequest(this.request, data);
    }

    private HostTrackerResponse registerHost() {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.registerHost(address);

        return HostTrackerResponse.fromRequest(this.request);
    }

    private HostTrackerResponse unregisterHost() {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.unregisterHost(address);

        return HostTrackerResponse.fromRequest(this.request);
    }

    private Matcher getDataMatcher(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.request.getData());

        if (!matcher.find()) throw ServerException.badRequestException();

        return matcher;
    }
}
