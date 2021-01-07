import HostTracker.*;
import TcpServer.TcpServer;

public class HostTrackerMain {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        HostTrackerState.initialize(args[1]);

        try {
            TcpServer<HostTrackerResponse, HostTrackerController> server = new TcpServer<HostTrackerResponse, HostTrackerController>(serverPort)
                    .useRequestReader(new HostTrackerRequestReader())
                    .useResponseWriter(new HostTrackerResponseWriter())
                    .useController(new HostTrackerController());

            server.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
