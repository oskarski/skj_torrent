package Host.MenuAction;

import Host.Client.ListFilesItem;
import Host.HostState;
import Host.Services.RemoteFileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class PullFileMenuAction implements MenuAction {
    private final RemoteFileService remoteFileService = new RemoteFileService();
    private ArrayList<ListFilesItem> fileList = new ArrayList<>();

    @Override
    public String getName() {
        return "Pull file";
    }

    @Override
    public void call() {
        ArrayList<String> hosts = HostState.hostTrackerClient.listHosts();
        HashMap<String, HashSet<String>> hostsByFileHashes = new HashMap<>();
        int i = 0;

        for (String hostAddress : hosts) {
            if (hostAddress.equals(HostState.getHostTcpServer().getAddress())) continue;
            System.out.println(" -> " + hostAddress);

            for (ListFilesItem listFilesItem : HostState.hostClient.listFiles(hostAddress)) {
                fileList.add(listFilesItem);

                HashSet<String> hostAddresses = hostsByFileHashes.get(listFilesItem.fileHash);

                if (hostAddresses == null) hostAddresses = new HashSet<>();

                hostAddresses.add(hostAddress);

                hostsByFileHashes.put(listFilesItem.fileHash, hostAddresses);
                System.out.println("    [" + i++ + "] " + listFilesItem.fileName + " " + listFilesItem.size + "B");
            }
        }

        int selectedFileIndex = this.readFileIndex();
        ListFilesItem selectedListFilesItem = this.fileList.get(selectedFileIndex);

        remoteFileService.pull(selectedListFilesItem, hostsByFileHashes.get(selectedListFilesItem.fileHash));
    }

    private int readFileIndex() {
        try {
            System.out.print("Choose file: ");
            Scanner scanner = new Scanner(System.in);

            int fileIndex = scanner.nextInt();

            if (!this.isValidFileIndex(fileIndex)) {
                this.renderInvalidFileIndexMessage();
                return this.readFileIndex();
            }

            return fileIndex;
        } catch (Exception exception) {
            this.renderInvalidFileIndexMessage();
            return this.readFileIndex();
        }
    }

    private void renderInvalidFileIndexMessage() {
        System.out.println("Invalid file!");
    }

    private boolean isValidFileIndex(int maybeFileIndex) {
        return maybeFileIndex >= 0 && maybeFileIndex < this.fileList.size();
    }
}
