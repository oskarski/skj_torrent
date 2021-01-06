package HostTracker;

import Server.ResponseWriter;
import Server.ServerException;

import java.io.BufferedWriter;
import java.io.IOException;

public class HostTrackerResponseWriter implements ResponseWriter<HostTrackerResponse> {
    @Override
    public void send(HostTrackerResponse response, BufferedWriter bufferedWriter) {
        String data = response.getMethod() + " " + response.getCode() + "\r\n";
        data += response.getData() + "\r\n\r\n";

        this.write(data, bufferedWriter);
    }

    @Override
    public void sendException(ServerException serverException, BufferedWriter bufferedWriter) {
        String data = serverException.getMessage() + " " + serverException.getCode() + "\r\n";
        data += "\r\n\r\n";

        this.write(data, bufferedWriter);
    }

    private void write(String data, BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
