package Server;

import java.io.BufferedWriter;

public interface ResponseWriter<ResponseType extends Response> {
    void send(ResponseType response, BufferedWriter bufferedWriter);

    void sendException(ServerException serverException, BufferedWriter bufferedWriter);
}
