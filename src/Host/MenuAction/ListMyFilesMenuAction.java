package Host.MenuAction;

import Host.HostState;

import java.io.File;

public class ListMyFilesMenuAction implements MenuAction {
    @Override
    public String getName() {
        return "List my files";
    }

    @Override
    public void call() {
        File directory = new File(HostState.getWorkspacePathname());

        for (File file : directory.listFiles()) {
            System.out.println(" -> " + file.getName() + " " + file.length() + "B");
        }
    }
}
