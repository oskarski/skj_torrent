package Host.Services;

import Host.Client.ListFilesItem;
import Host.HostState;
import utils.FileRepository;

import java.io.File;

public class RemoteFileService {
    private final LocalFileService localFileService = new LocalFileService();

    public void pull(ListFilesItem listFilesItem, String hostAddress) {
        try {
            String fileHash = listFilesItem.fileHash;
            File fileHashDirectory = this.getFileHashDirectory(fileHash);
            int totalChunks = this.getNumberOfChunks(listFilesItem.size);

            for (int chunk = 0; chunk < totalChunks; chunk++) {
                if (!this.isChunkPulled(fileHash, chunk)) {
                    byte[] chunkBytes = HostState.hostClient.pullFileChunk(hostAddress, fileHash, chunk);

                    FileRepository.write(new File(getFileChunkPathname(fileHash, chunk)), chunkBytes);
                }
            }

            if (countPulledChunks(fileHash) < totalChunks) {
                pull(listFilesItem, hostAddress);
                return;
            }

            localFileService.createOutputFileFromChunks(fileHash, listFilesItem.fileName, totalChunks);
            localFileService.deleteDirectory(fileHashDirectory);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    private int getNumberOfChunks(int fileSize) {
        return (int) Math.ceil(fileSize * 1.0 / HostState.chunkSize());
    }

    private String getFileHashDirectoryPathname(String fileHash) {
        return HostState.getWorkspaceTmpPathname() + "/" + fileHash;
    }

    private String getFileChunkPathname(String fileHash, int chunk) {
        return getFileHashDirectoryPathname(fileHash) + "/" + chunk + ".chunk";
    }

    private File getFileHashDirectory(String fileHash) {
        File fileHashDirectory = new File(getFileHashDirectoryPathname(fileHash));

        if (!fileHashDirectory.exists()) fileHashDirectory.mkdirs();

        return fileHashDirectory;
    }

    private boolean isChunkPulled(String fileHash, int chunk) {
        return localFileService.fileExists(getFileChunkPathname(fileHash, chunk));
    }

    private int countPulledChunks(String fileHash) {
        return localFileService.countFilesInDirectory(getFileHashDirectory(fileHash));
    }
}
