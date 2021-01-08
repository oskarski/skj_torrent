package Tcp;

import Tcp.TcpServer.RequestMethodValidator;
import Tcp.TcpServer.ServerException;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestReader {
    RequestMethodValidator requestMethodValidator;

    public RequestReader(RequestMethodValidator requestMethodValidator) {
        this.requestMethodValidator = requestMethodValidator;
    }

    public Request readRequest(BufferedReader bufferedReader) {
        try {
            String method = bufferedReader.readLine();

            if (!requestMethodValidator.isValid(method)) throw ServerException.badRequestException();

            String dataLine = bufferedReader.readLine();
            StringBuilder data = new StringBuilder(dataLine);

            while (dataLine != null && !dataLine.isEmpty()) {
                dataLine = bufferedReader.readLine();
                data.append("\r\n").append(dataLine);
            }

            return new Request(method, data.toString());
        } catch (IOException exception) {
            throw ServerException.badRequestException();
        }
    }
}
