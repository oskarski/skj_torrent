package Host.UI;

import Host.State;
import Host.UI.MenuAction.QuitMenuAction;

public class HostUIThread implements Runnable {
    private HostUIThread() {

    }

    public static HostUIThread create() {
        return new HostUIThread();
    }

    @Override
    public void run() {
        Menu menu = new Menu().addMenuAction(new QuitMenuAction());

        while (State.getIsHostRunning()) {
            menu.render();
            int action = menu.readMenuAction();
            menu.callMenuAction(action);
        }
    }
}
