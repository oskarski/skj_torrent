package TcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer<ResponseType extends Response, ControllerType extends Controller<Request, ResponseType>> {
    private boolean isRunning = false;
    private int currentServerThreads = 0;
    private RequestReader requestReader;
    private ResponseWriter<ResponseType> responseWriter;
    private ControllerType controller;

    private final ServerSocket serverSocket;
    private final int maxServerThreads = 6;

    public TcpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    public TcpServer<ResponseType, ControllerType> useRequestReader(RequestReader requestReader) {
        this.requestReader = requestReader;

        return this;
    }

    public TcpServer<ResponseType, ControllerType> useResponseWriter(ResponseWriter<ResponseType> responseWriter) {
        this.responseWriter = responseWriter;

        return this;
    }

    public TcpServer<ResponseType, ControllerType> useController(ControllerType controller) {
        this.controller = controller;

        return this;
    }

    public void start() throws Exception {
        if (!canBeStarted()) throw new Exception("Can't start the server!");

        isRunning = true;

        while (isRunning && canAcceptNewConnection()) {
            Socket client = serverSocket.accept();

            new Thread(() -> handleConnection(client, requestReader, responseWriter, controller)).start();
        }

        stop();
    }

    private boolean canBeStarted() {
        return requestReader != null && responseWriter != null && controller != null;
    }

    private boolean canAcceptNewConnection() {
        return currentServerThreads < maxServerThreads;
    }

    private void handleConnection(Socket clientSocket, RequestReader requestReader, ResponseWriter<ResponseType> responseWriter, ControllerType controller) {
        currentServerThreads++;

        ServerRequestThread.fromClientSocket(clientSocket, requestReader, responseWriter, controller);

        currentServerThreads--;
    }

    public void stop() throws IOException {
        isRunning = false;
        serverSocket.close();
    }
}
