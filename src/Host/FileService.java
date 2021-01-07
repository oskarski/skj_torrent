package Host;

import Host.Transport.ListFilesItem;
import utils.FileHasher;
import utils.FileRepository;

import java.io.File;
import java.io.IOException;

public class FileService {
    public void pull(ListFilesItem listFilesItem, String hostAddress) {
        try {
            File fileHashDirectory = this.getFileHashDirectory(listFilesItem.fileHash);
            int totalChunks = this.getNumberOfChunks(listFilesItem.size);

            for (int chunk = 0; chunk < totalChunks; chunk++) {
                if (!this.isChunkPulled(listFilesItem.fileHash, chunk)) {
                    byte[] chunkBytes = HostState.hostClient.pullFileChunk(hostAddress, listFilesItem.fileHash, chunk);

                    FileRepository.write(new File(this.getFileChunkPathname(listFilesItem.fileHash, chunk)), chunkBytes);
                }
            }

            if (this.countPulledChunks(listFilesItem.fileHash) < totalChunks) {
                this.pull(listFilesItem, hostAddress);
                return;
            }

            this.createOutputFile(listFilesItem.fileHash, listFilesItem.fileName, totalChunks);


            this.deleteFileHashDirectory(fileHashDirectory);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
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

    private int getNumberOfChunks(int fileSize) {
        return (int) Math.ceil(fileSize * 1.0 / HostState.chunkSize());
    }

    private String getFileHashDirectoryPathname(String fileHash) {
        return HostState.getWorkspaceTmpPathname() + "/" + fileHash;
    }

    private String getFileChunkPathname(String fileHash, int chunk) {
        return HostState.getWorkspaceTmpPathname() + "/" + fileHash + "/" + chunk + ".chunk";
    }

    private File getFileHashDirectory(String fileHash) {
        File fileHashDirectory = new File(this.getFileHashDirectoryPathname(fileHash));

        if (!fileHashDirectory.exists()) fileHashDirectory.mkdirs();

        return fileHashDirectory;
    }

    private boolean isChunkPulled(String fileHash, int chunk) {
        return new File(this.getFileChunkPathname(fileHash, chunk)).exists();
    }

    private int countPulledChunks(String fileHash) {
        File fileHashDirectory = this.getFileHashDirectory(fileHash);

        return fileHashDirectory.list().length;
    }

    private boolean filePathnameExists(String filePathname) {
        return new File(filePathname).exists();
    }

    private void createOutputFile(String fileHash, String fileName, int totalChunks) {
        String filePathname = HostState.getWorkspacePathname() + "/" + fileName;

        if (this.filePathnameExists(filePathname)) {
            this.createOutputFile(fileHash, "copy_" + fileName, totalChunks);

            return;
        }

        File outputFile = new File(filePathname);

        try {
            for (int chunk = 0; chunk < totalChunks; chunk++) {
                File chunkFile = new File(this.getFileChunkPathname(fileHash, chunk));

                FileRepository.append(outputFile, FileRepository.read(chunkFile));
            }
        } catch (IOException ioException) {
            throw new RuntimeException();
        }
    }

    private void deleteFileHashDirectory(File fileHashDirectory) {
        for (File file : fileHashDirectory.listFiles()) {
            file.delete();
        }

        fileHashDirectory.delete();
    }
}
