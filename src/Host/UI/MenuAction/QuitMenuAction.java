package Host.UI.MenuAction;

import Host.HostState;

public class QuitMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "Quit";
    }

    @Override
    public void call() {
        HostState.quitProgram();
    }
}
