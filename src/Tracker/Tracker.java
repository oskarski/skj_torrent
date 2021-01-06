package Tracker;

import utils.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tracker {
    private final TrackerRequest trackerRequest;
    private final TrackerService trackerService = new TrackerService();

    private Tracker(TrackerRequest trackerRequest) {
        this.trackerRequest = trackerRequest;
    }

    public static TrackerResponse fromTrackerRequest(TrackerRequest trackerRequest) throws TrackerException {
        Tracker self = new Tracker(trackerRequest);

        if (trackerRequest.getMethod().equals(TrackerMethod.T_GET)) return self.get();
        if (trackerRequest.getMethod().equals(TrackerMethod.T_POST)) return self.post();
        if (trackerRequest.getMethod().equals(TrackerMethod.T_DELETE)) return self.delete();

        return null;
    }

    private TrackerResponse get() throws TrackerException {
        if (!this.trackerRequest.getData().isEmpty()) throw TrackerException.badRequestException();

        String data = this.trackerService.getTorrentData(this.trackerRequest.getTorrentHash());

        return TrackerResponse.fromRequest(trackerRequest, data);
    }

    private TrackerResponse post() throws TrackerException {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")\\/(" + Regex.numberOfChunksRegex() + ")$");

        String address = matcher.group(1);
        int numberOfChunks = Integer.parseInt(matcher.group(2));

        this.trackerService.upsertTorrent(this.trackerRequest.getTorrentHash(), address, numberOfChunks);

        return TrackerResponse.fromRequest(this.trackerRequest);
    }

    private TrackerResponse delete() throws TrackerException {
        Matcher matcher = this.getDataMatcher("^(" + Regex.addressRegex() + ")$");

        String address = matcher.group(1);

        this.trackerService.deleteFromTorrent(this.trackerRequest.getTorrentHash(), address);

        return TrackerResponse.fromRequest(this.trackerRequest);
    }

    private Matcher getDataMatcher(String regex) throws TrackerException {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(this.trackerRequest.getData());

        if (!matcher.find()) throw TrackerException.badRequestException();

        return matcher;
    }
}
