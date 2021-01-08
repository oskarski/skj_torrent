import HostTracker.HostTrackerController;
import HostTracker.HostTrackerMethod;
import Tcp.RequestReader;
import Tcp.Response;
import Tcp.ResponseWriter;
import Tcp.TcpServer.TcpServer;

public class HostTrackerMain {
    public static void main(String[] args) {
        int serverPort = Integer.parseInt(args[0]);

        try {
            TcpServer<Response, HostTrackerController> server = new TcpServer<Response, HostTrackerController>(serverPort)
                    .useRequestReader(new RequestReader(HostTrackerMethod::isValidMethod))
                    .useResponseWriter(new ResponseWriter())
                    .useController(new HostTrackerController());

            server.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
