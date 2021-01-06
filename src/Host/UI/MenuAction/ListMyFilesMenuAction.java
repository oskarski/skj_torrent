package Host.UI.MenuAction;

import Host.State;

import java.io.File;

public class ListMyFilesMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List my files";
    }

    @Override
    public void call() {
        File directory = new File(State.getWorkspacePathname());

        for (File file : directory.listFiles()) {
            System.out.println("  " + file.getName() + " " + file.length() + "B");
        }
    }
}
