package Tracker;

import java.io.BufferedReader;
import java.io.IOException;

public class TrackerRequest {
    private final String method;
    private final String torrentHash;
    private final String data;

    public TrackerRequest(String method, String torrentHash, String data) {
        this.method = method;
        this.torrentHash = torrentHash;
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public String getTorrentHash() {
        return torrentHash;
    }

    public String getData() {
        return data;
    }

    public static TrackerRequest fromBufferedReader(BufferedReader bufferedReader) throws TrackerException {
        try {
            String method = bufferedReader.readLine();
            String torrentHash = bufferedReader.readLine();

            if (!TrackerMethod.isValidMethod(method)) throw TrackerException.badRequestException();
            if (torrentHash.isEmpty()) throw TrackerException.badRequestException();

            String dataLine = bufferedReader.readLine();
            StringBuilder data = new StringBuilder(dataLine);

            while (dataLine != null && !dataLine.isEmpty()) {
                dataLine = bufferedReader.readLine();
                data.append(dataLine);
            }

            return new TrackerRequest(method, torrentHash, data.toString());
        } catch (IOException exception) {
            throw TrackerException.badRequestException();
        }
    }
}
