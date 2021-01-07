package TcpServer;

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

    public static <RequestType extends Request, ResponseType extends Response, ControllerType extends Controller<RequestType, ResponseType>> void fromClientSocket(Socket clientSocket, RequestReader<RequestType> requestReader, ResponseWriter<ResponseType> responseWriter, ControllerType controller) {
        ServerRequestThread self = new ServerRequestThread(clientSocket);

        try {
            RequestType request = requestReader.readRequest(self.bufferedReader);
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
