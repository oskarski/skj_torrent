package Host.UI.MenuAction;

import Host.Client.ListFilesItem;
import Host.HostState;
import utils.Regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListFilesOnHostMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List files on host";
    }

    @Override
    public void call() {
        String hostAddress = this.readHostAddress();

        for (ListFilesItem listFilesItem : HostState.hostClient.listFiles(hostAddress)) {
            System.out.println("    -> " + listFilesItem.fileName + " " + listFilesItem.size + "B");
        }
    }

    private String readHostAddress() {
        try {
            System.out.print("Host address: ");
            Scanner scanner = new Scanner(System.in);

            String hostAddress = scanner.nextLine();

            if (!this.isValidHostAddress(hostAddress)) {
                this.renderHostAddressMessage();
                return this.readHostAddress();
            }

            return hostAddress;
        } catch (Exception exception) {
            this.renderHostAddressMessage();
            return this.readHostAddress();
        }
    }

    private void renderHostAddressMessage() {
        System.out.println("Invalid host address!");
        ;
    }

    private boolean isValidHostAddress(String maybeHostAddress) {
        Pattern pattern = Pattern.compile(Regex.groupedAddressRegex());
        Matcher matcher = pattern.matcher(maybeHostAddress);

        if (!matcher.find()) return false;

        return HostState.hostClient.ping(maybeHostAddress);
    }
}
