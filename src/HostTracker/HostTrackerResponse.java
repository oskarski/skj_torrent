package HostTracker;

import Server.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostTrackerResponse extends Response {
    protected HostTrackerResponse(String method, int code, String data) {
        super(method, code, data);
    }

    public static HostTrackerResponse fromRequest(HostTrackerRequest hostTrackerRequest, String data) {
        return new HostTrackerResponse(hostTrackerRequest.getMethod(), 200, data);
    }

    public static HostTrackerResponse fromRequest(HostTrackerRequest hostTrackerRequest) {
        return HostTrackerResponse.fromRequest(hostTrackerRequest, "");
    }

    public static HostTrackerResponse fromBufferedReader(BufferedReader bufferedReader) {
        try {
            String responseLine = bufferedReader.readLine();

            Pattern pattern = Pattern.compile("^([a-zA-Z_]+) (\\d+)$");
            Matcher matcher = pattern.matcher(responseLine);

            if (!matcher.find()) return null;

            String method = matcher.group(1);
            int code = Integer.parseInt(matcher.group(2));

            StringBuilder data = new StringBuilder();

            while (responseLine != null && !responseLine.isEmpty()) {
                responseLine = bufferedReader.readLine();
                data.append(responseLine);
            }

            return new HostTrackerResponse(method, code, data.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }
}
