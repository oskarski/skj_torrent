import Host.State;
import Host.UI.HostUIThread;

public class HostMain {
    public static void main(String[] args) {
        int hostPort = Integer.parseInt(args[0]);
        String workspacePathname = args[1];

        State.init(workspacePathname);

        try {
//            TODO REGISTER IN TRACKER

            new Thread(HostUIThread.create()).start();

//            TODO LISTEN ON INCOMING CONNECTIONS no hostPort
        } catch (Exception exception) {
//            TODO UNREGISTER IN TRACKER
        }
    }
}
