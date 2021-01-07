package Host.MenuAction;

import Host.LocalFileService;

import java.io.File;
import java.util.ArrayList;

public class ListMyFilesMenuAction implements MenuAction {
    private final LocalFileService localFileService = new LocalFileService();

    @Override
    public String getName() {
        return "List my files";
    }

    @Override
    public void call() {
        ArrayList<File> files = localFileService.getFileList();

        if (files.size() == 0) {
            System.out.println("No files");

            return;
        }

        for (File file : files) {
            System.out.println(" -> " + file.getName() + " " + file.length() + "B");
        }
    }
}
