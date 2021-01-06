package HostTracker;

import utils.Regex;
import Server.Controller;
import Server.ServerException;

import java.util.regex.Matcher;

public class HostTrackerController extends Controller<HostTrackerRequest, HostTrackerResponse> {
    private final HostTrackerService hostTrackerService = new HostTrackerService();

    @Override
    public HostTrackerResponse handleRequest(HostTrackerRequest request) {
        this.request = request;

        if (this.request.getMethod().equals(HostTrackerMethod.LIST_HOSTS)) return this.listHosts();
        if (this.request.getMethod().equals(HostTrackerMethod.REGISTER_HOST)) return this.registerHost();
        if (this.request.getMethod().equals(HostTrackerMethod.UNREGISTER_HOST)) return this.unregisterHost();

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
}
