package HostTracker;

import Tcp.Request;
import Tcp.Response;
import Tcp.TcpServer.Controller;
import Tcp.TcpServer.ServerException;
import utils.Regex;

import java.util.regex.Matcher;

public class HostTrackerController extends Controller<Request, Response> {
    private final HostTrackerService hostTrackerService = new HostTrackerService();

    @Override
    public Response handleRequest(Request request) {
        this.request = request;

        if (this.request.getMethod().equals(HostTrackerMethod.LIST_HOSTS)) return this.listHosts();
        if (this.request.getMethod().equals(HostTrackerMethod.REGISTER_HOST)) return this.registerHost();
        if (this.request.getMethod().equals(HostTrackerMethod.UNREGISTER_HOST)) return this.unregisterHost();

        return null;
    }

    private Response listHosts() {
        if (!this.request.getData().isEmpty()) throw ServerException.badRequestException();

        String data = this.hostTrackerService.getHostsData();

        return new Response(this.request.getMethod(), data);
    }

    private Response registerHost() {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.registerHost(address);

        return new Response(this.request.getMethod());
    }

    private Response unregisterHost() {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.hostTrackerService.unregisterHost(address);

        return new Response(this.request.getMethod());
    }
}
