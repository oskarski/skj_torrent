package Host.Transport;

import Host.HostState;
import HostTracker.HostTrackerMethod;
import HostTracker.HostTrackerResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class HostTrackerClient {
    public static ArrayList<String> listHosts() {
        HostTrackerResponse response = callTracker(HostTrackerMethod.LIST_HOSTS);

        return new ArrayList<>(Arrays.asList(response.getData().split("\r\n")));
    }

    private static HostTrackerResponse callTracker(String method) {
        return callTracker(method, "");
    }

    private static HostTrackerResponse callTracker(String method, String data) {
        try {
            InetAddress serverAddress = InetAddress.getByName(HostState.getHostTrackerIP());
            Socket socket = new Socket(serverAddress, HostState.getHostTrackerPort());

            InputStream inStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            InputStreamReader inReader = new InputStreamReader(inStream);
            OutputStreamWriter outWriter = new OutputStreamWriter(outStream);
            BufferedReader bufferedReader = new BufferedReader(inReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outWriter);

            bufferedWriter.write(method + "\r\n");
            bufferedWriter.write(data + "\r\n");
            bufferedWriter.write("\r\n\r\n");

            bufferedWriter.flush();

            HostTrackerResponse response = HostTrackerResponse.fromBufferedReader(bufferedReader);

            inStream.close();
            outStream.close();
            socket.close();

            return response;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return null;
    }
}
