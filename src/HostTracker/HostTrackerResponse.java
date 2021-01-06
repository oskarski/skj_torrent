package HostTracker;

import java.io.BufferedWriter;
import java.io.IOException;

public class HostTrackerResponse {
    private final String method;
    private final int code;
    private final String data;

    public HostTrackerResponse(String method, int code, String data) {
        this.method = method;
        this.code = code;
        this.data = data;
    }

    public static HostTrackerResponse fromException(HostTrackerException hostTrackerException) {
        return new HostTrackerResponse(hostTrackerException.getMessage(), hostTrackerException.getCode(), "");
    }

    public static HostTrackerResponse fromRequest(HostTrackerRequest hostTrackerRequest, String data) {
        return new HostTrackerResponse(hostTrackerRequest.getMethod(), 200, data);
    }

    public static HostTrackerResponse fromRequest(HostTrackerRequest hostTrackerRequest) {
        return HostTrackerResponse.fromRequest(hostTrackerRequest, "");
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
