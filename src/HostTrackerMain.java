import HostTracker.HostTrackerRequestThread;
import HostTracker.HostTrackerState;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HostTrackerMain {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        HostTrackerState.initialize(args[1]);

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                Socket client = serverSocket.accept();

                new Thread(() -> HostTrackerRequestThread.fromSocket(client)).start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
