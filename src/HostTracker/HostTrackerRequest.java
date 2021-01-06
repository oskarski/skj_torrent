package HostTracker;

import utils.transport.ServerException;

import java.io.BufferedReader;
import java.io.IOException;

public class HostTrackerRequest {
    private final String method;
    private final String data;

    public HostTrackerRequest(String method, String data) {
        this.method = method;
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public String getData() {
        return data;
    }

    public static HostTrackerRequest fromBufferedReader(BufferedReader bufferedReader) {
        try {
            String method = bufferedReader.readLine();

            if (!HostTrackerMethod.isValidMethod(method)) throw ServerException.badRequestException();

            String dataLine = bufferedReader.readLine();
            StringBuilder data = new StringBuilder(dataLine);

            while (dataLine != null && !dataLine.isEmpty()) {
                dataLine = bufferedReader.readLine();
                data.append(dataLine);
            }

            return new HostTrackerRequest(method, data.toString());
        } catch (IOException exception) {
            throw ServerException.badRequestException();
        }
    }
}
