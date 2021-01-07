package TcpServer;

import java.io.BufferedWriter;
import java.io.IOException;

public class ResponseWriter<ResponseType extends Response> {
    public void send(ResponseType response, BufferedWriter bufferedWriter) {
        String data = response.getMethod() + " " + response.getCode() + "\r\n";
        data += response.getData() + "\r\n\r\n";

        this.write(data, bufferedWriter);
    }

    public void sendException(ServerException serverException, BufferedWriter bufferedWriter) {
        String data = serverException.getMessage() + " " + serverException.getCode() + "\r\n";
        data += "\r\n\r\n";

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
