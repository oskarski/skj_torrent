package Tcp.TcpServer;

import Tcp.Request;
import Tcp.RequestReader;
import Tcp.Response;
import Tcp.ResponseWriter;

import java.io.*;
import java.net.Socket;

public class ServerRequestThread {
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    private ServerRequestThread(Socket clientSocket) {
        try {
            this.inputStream = clientSocket.getInputStream();
            this.inputStreamReader = new InputStreamReader(inputStream);
            this.bufferedReader = new BufferedReader(this.inputStreamReader);

            this.outputStream = clientSocket.getOutputStream();
            this.outputStreamWriter = new OutputStreamWriter(outputStream);
            this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static <ResponseType extends Response, ControllerType extends Controller<Request, ResponseType>> void fromClientSocket(Socket clientSocket, RequestReader requestReader, ResponseWriter responseWriter, ControllerType controller) {
        ServerRequestThread self = new ServerRequestThread(clientSocket);

        try {
            Request request = requestReader.readRequest(self.bufferedReader);
            ResponseType response = controller.handleRequest(request);

            responseWriter.send(response, self.bufferedWriter);
        } catch (ServerException exception) {
            responseWriter.sendException(exception, self.bufferedWriter);
        }

        self.closeStreams();
    }

    private void closeStreams() {
        try {
            this.inputStream.close();
            this.inputStreamReader.close();
            this.bufferedReader.close();
            this.outputStream.close();
            this.outputStreamWriter.close();
            this.bufferedWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
