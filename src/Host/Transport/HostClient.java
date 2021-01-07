package Host.Transport;

import Host.HostMethod;
import Tcp.*;
import utils.Regex;

import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostClient {
    private final TcpClient tcpClient;

    public HostClient() {
        this.tcpClient = new TcpClient(new RequestWriter(), new ResponseReader());
    }

    public ArrayList<ListFilesItem> listFiles(String hostAddress) {
        Response response = tcpClient.call(hostAddress, new Request(HostMethod.LIST_FILES));

        ArrayList<ListFilesItem> listFilesItems = new ArrayList<>();

        for (String fileDataLine : response.getData().split("\r\n")) {
            Pattern pattern = Pattern.compile("^(" + Regex.fileHashRegex() + ") (" + Regex.digits() + ") (" + (Regex.fileNameRegex()) + ")$");
            Matcher matcher = pattern.matcher(fileDataLine);

            if (matcher.find()) {
                listFilesItems.add(new ListFilesItem(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3)));
            }
        }

        return listFilesItems;
    }

    public boolean ping(String hostAddress) {
        try {
            Response response = tcpClient.call(hostAddress, new Request(HostMethod.PING));

            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public byte[] pullFileChunk(String hostAddress, String fileHash, int chunk) {
        String requestData = fileHash + " " + chunk;

        Response response = tcpClient.call(hostAddress, new Request(HostMethod.PULL_FILE, requestData));

        return Base64.getDecoder().decode(response.getData().trim());
    }
}
