package Client;

import TcpServer.Request;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class RequestWriter<RequestType extends Request> {
    public void send(RequestType request, BufferedWriter bufferedWriter) {
        String data = request.getMethod() + "\r\n";
        data += request.getData() + "\r\n\r\n\r\n";

        this.write(data, bufferedWriter);
    }

    protected void write(String data, BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
