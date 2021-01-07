package TcpServer;

import java.io.BufferedReader;

public interface RequestReader {
    Request readRequest(BufferedReader bufferedReader);
}
