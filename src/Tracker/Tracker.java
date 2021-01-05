package Tracker;

public class Tracker {
    public static TrackerResponse fromTrackerRequest(TrackerRequest trackerRequest) {
        if (trackerRequest.getMethod().equals(TrackerMethod.T_GET)) return get(trackerRequest);
        if (trackerRequest.getMethod().equals(TrackerMethod.T_POST)) return post(trackerRequest);
        if (trackerRequest.getMethod().equals(TrackerMethod.T_PUT)) return put(trackerRequest);
        if (trackerRequest.getMethod().equals(TrackerMethod.T_DELETE)) return delete(trackerRequest);

        return null;
    }

    private static TrackerResponse get(TrackerRequest trackerRequest) {
        return TrackerResponse.fromRequest(trackerRequest, "todo get");
    }

    private static TrackerResponse post(TrackerRequest trackerRequest) {
        return TrackerResponse.fromRequest(trackerRequest, "todo post");
    }

    private static TrackerResponse put(TrackerRequest trackerRequest) {
        return TrackerResponse.fromRequest(trackerRequest, "todo put");
    }

    private static TrackerResponse delete(TrackerRequest trackerRequest) {
        return TrackerResponse.fromRequest(trackerRequest, "todo delete");
    }
}
