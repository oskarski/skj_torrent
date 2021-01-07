import HostTracker.HostTrackerController;
import HostTracker.HostTrackerRequestReader;
import HostTracker.HostTrackerResponse;
import HostTracker.HostTrackerState;
import TcpServer.ResponseWriter;
import TcpServer.TcpServer;

public class HostTrackerMain {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        HostTrackerState.initialize(args[1]);

        try {
            TcpServer<HostTrackerResponse, HostTrackerController> server = new TcpServer<HostTrackerResponse, HostTrackerController>(serverPort)
                    .useRequestReader(new HostTrackerRequestReader())
                    .useResponseWriter(new ResponseWriter<HostTrackerResponse>())
                    .useController(new HostTrackerController());

            server.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
