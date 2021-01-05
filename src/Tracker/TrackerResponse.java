package Tracker;

import java.io.BufferedWriter;
import java.io.IOException;

public class TrackerResponse {
    private final String method;
    private final int code;
    private final String data;

    public TrackerResponse(String method, int code, String data) {
        this.method = method;
        this.code = code;
        this.data = data;
    }

    public static TrackerResponse fromException(BadRequestException badRequestException) {
        return new TrackerResponse(badRequestException.getMessage(), badRequestException.code, "");
    }

    public static TrackerResponse fromRequest(TrackerRequest trackerRequest, String data) {
        return new TrackerResponse(trackerRequest.getMethod(), 200, data);
    }

    public void send(BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write(this.method + " " + this.code + "\r\n" + this.data + "\r\n\r\n");
            bufferedWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
