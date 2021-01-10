package Host;


import Host.MenuAction.ListHostsMenuAction;
import Host.MenuAction.ListMyFilesMenuAction;
import Host.MenuAction.ListPeerFilesMenuAction;
import Host.MenuAction.QuitMenuAction;
import Host.MenuAction.ListHostsFilesMenuAction;
import Host.MenuAction.ListFilesOnHostMenuAction;
import Host.MenuAction.PullFileMenuAction;
import Host.MenuAction.PushFileMenuAction;

public class HostUIThread implements Runnable {
    private HostUIThread() {

    }

    public static HostUIThread create() {
        return new HostUIThread();
    }

    @Override
    public void run() {
        try {
            Menu menu = new Menu()
                    .addMenuAction(new QuitMenuAction())
                    .addMenuAction(new ListMyFilesMenuAction());

            if (HostState.isH2hMode()) {
                menu.addMenuAction(new ListPeerFilesMenuAction());
            } else {
                menu.addMenuAction(new ListHostsMenuAction())
                        .addMenuAction(new ListHostsFilesMenuAction())
                        .addMenuAction(new ListFilesOnHostMenuAction());
            }
            menu.addMenuAction(new PullFileMenuAction())
                    .addMenuAction(new PushFileMenuAction());

            while (HostState.getIsHostRunning()) {
                menu.render();
                int action = menu.readMenuAction();
                menu.callMenuAction(action);
            }
        } catch (Exception exception) {
            System.out.println("Unknown error occurred. Try again");
            this.run();
        }
    }
}
