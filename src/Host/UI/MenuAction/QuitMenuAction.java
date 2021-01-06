package Host.UI.MenuAction;

import Host.State;

public class QuitMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "Quit";
    }

    @Override
    public void call() {
        State.setIsHostRunning(false);
    }
}
