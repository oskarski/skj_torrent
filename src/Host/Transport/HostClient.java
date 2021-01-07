package Host.Transport;

import Client.Client;
import Host.HostMethod;
import Host.HostRequest;
import Host.HostResponse;
import utils.Regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HostClient {
    private final Client<HostRequest, HostResponse> client;

    public HostClient() {
        this.client = new Client<HostRequest, HostResponse>(new HostRequestWriter(), new HostResponseReader());
    }

    public ArrayList<ListFilesItem> listFiles(String hostAddress) {
        HostResponse response = client.call(hostAddress, new HostRequest(HostMethod.LIST_FILES));

        ArrayList<ListFilesItem> listFilesItems = new ArrayList<>();

        for (String fileDataLine : response.getData().split("\r\n")) {
            Pattern pattern = Pattern.compile(Regex.groupedListFilesDataLineRegex());
            Matcher matcher = pattern.matcher(fileDataLine);

            if (matcher.find()) {
                listFilesItems.add(new ListFilesItem(matcher.group(1), Integer.parseInt(matcher.group(2)), matcher.group(3)));
            }
        }

        return listFilesItems;
    }
}
