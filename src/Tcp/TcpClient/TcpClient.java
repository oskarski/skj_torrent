package Tcp.TcpClient;

import Tcp.TcpServer.Request;
import Tcp.TcpServer.Response;
import utils.Regex;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcpClient<RequestType extends Request, ResponseType extends Response> {
    RequestWriter<RequestType> requestWriter;
    ResponseReader<ResponseType> responseReader;

    public TcpClient(RequestWriter<RequestType> requestWriter, ResponseReader<ResponseType> responseReader) {
        this.requestWriter = requestWriter;
        this.responseReader = responseReader;
    }

    public ResponseType call(String address, RequestType request) {
        try {
            InetAddress serverAddress = InetAddress.getByName(this.getHostIP(address));
            Socket socket = new Socket(serverAddress, this.getHostPort(address));

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            this.requestWriter.send(request, bufferedWriter);
            ResponseType response = this.responseReader.readResponse(bufferedReader);

            inputStream.close();
            outputStream.close();
            socket.close();

            return response;
        } catch (IOException e) {
            throw new RuntimeException("Can't perform request call!");
        }
    }

    private String getHostIP(String address) {
        Pattern pattern = Pattern.compile(Regex.groupedAddressRegex());
        Matcher matcher = pattern.matcher(address);

        if (!matcher.find()) return "";

        return matcher.group(1);
    }

    private int getHostPort(String address) {
        Pattern pattern = Pattern.compile(Regex.groupedAddressRegex());
        Matcher matcher = pattern.matcher(address);

        if (!matcher.find()) return -1;

        return Integer.parseInt(matcher.group(2));
    }
}
