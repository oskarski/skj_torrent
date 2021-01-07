package Host.Transport;

import Client.ResponseReader;
import HostTracker.HostTrackerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostTrackerResponseReader implements ResponseReader<HostTrackerResponse> {
    @Override
    public HostTrackerResponse readResponse(BufferedReader bufferedReader) {
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
                data.append(responseLine + "\r\n");
            }

            return HostTrackerResponse.create(method, code, data.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }
}
