package Host.MenuAction;

import Host.HostState;
import Host.Services.LocalFileService;
import Host.Services.RemoteFileService;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PushFileMenuAction implements MenuAction {
    private final LocalFileService localFileService = new LocalFileService();
    private final RemoteFileService remoteFileService = new RemoteFileService();

    @Override
    public String getName() {
        return "Push file";
    }

    @Override
    public void call() {
        System.out.println("Choose file: ");

        ArrayList<File> files = localFileService.getFileList();

        if (files.size() == 0) {
            System.out.println("No files to push");

            return;
        }

        int fileIndex = 0;

        for (File file : files) {
            System.out.println(" [" + fileIndex++ + "] " + file.getName() + " " + file.length() + "B");
        }

        int selectedFileIndex = readFileIndex(files.size());

        ArrayList<String> hosts = HostState.hostTrackerClient.listHosts();

        int hostIndex = 0;

        for (String host : hosts) {
            if (host.equals(HostState.getHostTcpServer().getAddress())) continue;
            System.out.println(" [" + hostIndex++ + "] " + host);
        }

        int selectedHostIndex = readHostIndex(hosts.size());

        System.out.println("Podaj nazwe dla wgranego pliku: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        remoteFileService.push(files.get(selectedFileIndex), hosts.get(selectedHostIndex), fileName);
    }

    private int readFileIndex(int maxIndex) {
        try {
            System.out.print("Choose file: ");
            Scanner scanner = new Scanner(System.in);

            int fileIndex = Integer.parseInt(scanner.nextLine());

            if (!this.isValidIndex(fileIndex, maxIndex)) {
                this.renderInvalidFileIndexMessage();
                return this.readFileIndex(maxIndex);
            }

            return fileIndex;
        } catch (Exception exception) {
            this.renderInvalidFileIndexMessage();
            return this.readFileIndex(maxIndex);
        }
    }

    private void renderInvalidFileIndexMessage() {
        System.out.println("Invalid file!");
    }

    private int readHostIndex(int maxIndex) {
        try {
            System.out.print("Choose host: ");
            Scanner scanner = new Scanner(System.in);

            int fileIndex = scanner.nextInt();

            if (!this.isValidIndex(fileIndex, maxIndex)) {
                this.renderInvalidHostIndexMessage();
                return this.readFileIndex(maxIndex);
            }

            return fileIndex;
        } catch (Exception exception) {
            this.renderInvalidHostIndexMessage();
            return this.readFileIndex(maxIndex);
        }
    }

    private void renderInvalidHostIndexMessage() {
        System.out.println("Invalid host!");
    }

    private boolean isValidIndex(int maybeFileIndex, int maxIndex) {
        return maybeFileIndex >= 0 && maybeFileIndex < maxIndex;
    }
}
