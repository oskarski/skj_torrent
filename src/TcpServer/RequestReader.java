package TcpServer;

import java.io.BufferedReader;

public interface RequestReader<RequestType extends Request> {
    RequestType readRequest(BufferedReader bufferedReader);
}
