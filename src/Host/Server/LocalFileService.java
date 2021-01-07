package Host.Server;

import Host.HostState;
import utils.FileHasher;
import utils.FileRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LocalFileService {
    public ArrayList<File> getFileList() {
        ArrayList<File> files = new ArrayList<>();
        File directory = new File(HostState.getWorkspacePathname());

        for (File file : directory.listFiles()) {
            if (!file.isDirectory()) files.add(file);
        }

        return files;
    }

    public File getFileByHash(String fileHash) {
        try {
            File directory = new File(HostState.getWorkspacePathname());

            for (File file : directory.listFiles()) {
                if (FileHasher.getFileHash(file).equals(fileHash)) return file;
            }

            return null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteDirectory(File directory) {
        if (!directory.isDirectory()) return;

        for (File fileOrDirectory : directory.listFiles()) {
            if (fileOrDirectory.isDirectory()) deleteDirectory(fileOrDirectory);

            fileOrDirectory.delete();
        }

        directory.delete();
    }

    public void createOutputFileFromChunks(String fileHash, String fileName, int totalChunks) {
        String filePathname = HostState.getWorkspacePathname() + "/" + fileName;

        if (fileExists(filePathname)) {
            createOutputFileFromChunks(fileHash, "copy_" + fileName, totalChunks);

            return;
        }

        File outputFile = new File(filePathname);

        try {
            for (int chunk = 0; chunk < totalChunks; chunk++) {
                File chunkFile = new File(this.getFileChunkPathname(fileHash, chunk));

                FileRepository.append(outputFile, FileRepository.read(chunkFile));
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String getFileChunkPathname(String fileHash, int chunk) {
        return HostState.getWorkspaceTmpPathname() + "/" + fileHash + "/" + chunk + ".chunk";
    }

    public boolean fileExists(String filePathname) {
        return new File(filePathname).exists();
    }

    public int countFilesInDirectory(File directory) {
        if (!directory.isDirectory()) return 0;

        return directory.list().length;
    }
}
