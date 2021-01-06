package HostTracker;

import Server.Request;
import Server.RequestReader;
import Server.ServerException;

import java.io.BufferedReader;
import java.io.IOException;

public class HostTrackerRequestReader implements RequestReader<HostTrackerRequest> {
    @Override
    public HostTrackerRequest readRequest(BufferedReader bufferedReader) {
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
