package Host.UI;

import Host.HostState;
import Host.UI.MenuAction.*;

public class HostUIThread implements Runnable {
    private HostUIThread() {

    }

    public static HostUIThread create() {
        return new HostUIThread();
    }

    @Override
    public void run() {
        Menu menu = new Menu()
                .addMenuAction(new QuitMenuAction())
                .addMenuAction(new ListMyFilesMenuAction())
                .addMenuAction(new ListHostsMenuAction())
                .addMenuAction(new ListHostsFilesMenuAction())
                .addMenuAction(new ListFilesOnHostMenuAction());

        while (HostState.getIsHostRunning()) {
            menu.render();
            int action = menu.readMenuAction();
            menu.callMenuAction(action);
        }
    }
}
