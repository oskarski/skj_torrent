package Tcp.TcpClient;

import Tcp.TcpServer.Request;

import java.io.BufferedWriter;
import java.io.IOException;

public class RequestWriter {
    public void send(Request request, BufferedWriter bufferedWriter) {
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
