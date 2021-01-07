package Host;

import TcpServer.RequestReader;
import TcpServer.ServerException;

import java.io.BufferedReader;
import java.io.IOException;

public class HostRequestReader implements RequestReader<HostRequest> {
    @Override
    public HostRequest readRequest(BufferedReader bufferedReader) {
        try {
            String method = bufferedReader.readLine();

            if (!HostMethod.isValidMethod(method)) throw ServerException.badRequestException();

            String dataLine = bufferedReader.readLine();
            StringBuilder data = new StringBuilder(dataLine);

            while (dataLine != null && !dataLine.isEmpty()) {
                dataLine = bufferedReader.readLine();
                data.append(dataLine);
            }

            return new HostRequest(method, data.toString());
        } catch (IOException exception) {
            throw ServerException.badRequestException();
        }
    }
}
