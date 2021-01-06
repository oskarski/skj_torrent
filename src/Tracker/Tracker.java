package Tracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tracker {
    private final TrackerRequest trackerRequest;
    private final TrackerService trackerService = new TrackerService();
    private final String addressRegex = "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}:[0-9]+";
    private final String numberOfChunksRegex = "[0-9]+";

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

    private TrackerResponse get() {
        return TrackerResponse.fromRequest(trackerRequest, "todo get");
    }

    private TrackerResponse post() throws TrackerException {
        Matcher matcher = this.getDataMatcher("^(" + this.addressRegex + ")\\/(" + this.numberOfChunksRegex + ")$");

        String address = matcher.group(1);
        int numberOfChunks = Integer.parseInt(matcher.group(2));

        this.trackerService.upsertTorrent(this.trackerRequest.getTorrentHash(), address, numberOfChunks);

        return TrackerResponse.fromRequest(this.trackerRequest);
    }

    private TrackerResponse delete() throws TrackerException {
        Matcher matcher = this.getDataMatcher("^(" + this.addressRegex + ")$");

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
