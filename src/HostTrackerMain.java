import HostTracker.HostTrackerController;
import HostTracker.HostTrackerRequestReader;
import HostTracker.HostTrackerState;
import TcpServer.Response;
import TcpServer.ResponseWriter;
import TcpServer.TcpServer;

public class HostTrackerMain {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        HostTrackerState.initialize(args[1]);

        try {
            TcpServer<Response, HostTrackerController> server = new TcpServer<Response, HostTrackerController>(serverPort)
                    .useRequestReader(new HostTrackerRequestReader())
                    .useResponseWriter(new ResponseWriter<Response>())
                    .useController(new HostTrackerController());

            server.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
