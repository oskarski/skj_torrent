import Tracker.TrackerRequestThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TrackerMain {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (true) {
                Socket client = serverSocket.accept();

                new Thread(() -> TrackerRequestThread.fromSocket(client)).start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
