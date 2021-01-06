package HostTracker;

import java.io.*;
import java.net.Socket;

public class HostTrackerRequestThread {
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    private HostTrackerRequestThread(Socket clientSocket) {
        try {
            this.inputStream = clientSocket.getInputStream();
            this.inputStreamReader = new InputStreamReader(inputStream);
            this.bufferedReader = new BufferedReader(this.inputStreamReader);

            this.outputStream = clientSocket.getOutputStream();
            this.outputStreamWriter = new OutputStreamWriter(outputStream);
            this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void fromSocket(Socket clientSocket) {
        HostTrackerRequestThread self = new HostTrackerRequestThread(clientSocket);

        try {
            HostTrackerRequest hostTrackerRequest = HostTrackerRequest.fromBufferedReader(self.bufferedReader);
            HostTrackerResponse hostTrackerResponse = HostTracker.fromTrackerRequest(hostTrackerRequest);

            hostTrackerResponse.send(self.bufferedWriter);
        } catch (HostTrackerException exception) {
            HostTrackerResponse.fromException(exception).send(self.bufferedWriter);
        }

        self.closeStreams();
    }

    private void closeStreams() {
        try {
            this.inputStream.close();
            this.inputStreamReader.close();
            this.bufferedReader.close();
            this.outputStream.close();
            this.outputStreamWriter.close();
            this.bufferedWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
