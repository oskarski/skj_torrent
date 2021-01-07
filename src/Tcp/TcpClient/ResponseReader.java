package Tcp.TcpClient;

import Tcp.TcpServer.Response;

import java.io.BufferedReader;

public interface ResponseReader<ResponseType extends Response> {
    ResponseType readResponse(BufferedReader bufferedReader);
}
