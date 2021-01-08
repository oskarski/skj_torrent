package Host.Services;

import Host.Client.ListFilesItem;
import Host.HostState;
import utils.FileHasher;
import utils.FileRepository;

import java.io.File;

public class RemoteFileService {
    private final LocalFileService localFileService = new LocalFileService();

    public void pull(ListFilesItem listFilesItem, String hostAddress) {
        try {
            String fileHash = listFilesItem.fileHash;
            File fileHashDirectory = this.getPullFileHashDirectory(fileHash);
            int totalChunks = this.getNumberOfChunks(listFilesItem.size);

            for (int chunk = 0; chunk < totalChunks; chunk++) {
                if (!this.isChunkPulled(fileHash, chunk)) {
                    byte[] chunkBytes = HostState.hostClient.pullFileChunk(hostAddress, fileHash, chunk);

                    FileRepository.write(new File(getPullFileChunkPathname(fileHash, chunk)), chunkBytes);
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

    public void push(File file, String hostAddress) {
        try {
            String fileHash = FileHasher.getFileHash(file);
            File fileHashDirectory = this.getPushFileHashDirectory(fileHash);
            int totalChunks = this.getNumberOfChunks((int) file.length());

            for (int chunk = 0; chunk < totalChunks; chunk++) {
                if (!this.isChunkPushed(fileHash, chunk)) {
                    byte[] chunkBytes = FileRepository.readChunk(file, (long) chunk * HostState.chunkSize(), HostState.chunkSize());

                    HostState.hostClient.pushFileChunk(hostAddress, fileHash, file.getName(), chunkBytes, chunk, totalChunks);
                }
            }

            if (countPushedChunks(fileHash) < totalChunks - 1) {
                push(file, hostAddress);
                return;
            }

            localFileService.deleteDirectory(fileHashDirectory);
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    private int getNumberOfChunks(int fileSize) {
        return (int) Math.ceil(fileSize * 1.0 / HostState.chunkSize());
    }

    private String getPullFileHashDirectoryPathname(String fileHash) {
        return HostState.getWorkspacePullTmpPathname() + "/" + fileHash;
    }

    private String getPushFileHashDirectoryPathname(String fileHash) {
        return HostState.getWorkspacePushTmpPathname() + "/" + fileHash;
    }

    private String getPullFileChunkPathname(String fileHash, int chunk) {
        return getPullFileHashDirectoryPathname(fileHash) + "/" + chunk + ".chunk";
    }

    private String getPushFileChunkPathname(String fileHash, int chunk) {
        return getPushFileHashDirectoryPathname(fileHash) + "/" + chunk + ".chunk";
    }

    private File getPullFileHashDirectory(String fileHash) {
        File fileHashDirectory = new File(getPullFileHashDirectoryPathname(fileHash));

        if (!fileHashDirectory.exists()) fileHashDirectory.mkdirs();

        return fileHashDirectory;
    }

    private File getPushFileHashDirectory(String fileHash) {
        File fileHashDirectory = new File(getPushFileHashDirectoryPathname(fileHash));

        if (!fileHashDirectory.exists()) fileHashDirectory.mkdirs();

        return fileHashDirectory;
    }

    private boolean isChunkPulled(String fileHash, int chunk) {
        return localFileService.fileExists(getPullFileChunkPathname(fileHash, chunk));
    }

    private boolean isChunkPushed(String fileHash, int chunk) {
        return localFileService.fileExists(getPushFileChunkPathname(fileHash, chunk));
    }

    private int countPulledChunks(String fileHash) {
        return localFileService.countFilesInDirectory(getPullFileHashDirectory(fileHash));
    }

    private int countPushedChunks(String fileHash) {
        return localFileService.countFilesInDirectory(getPushFileHashDirectory(fileHash));
    }
}
