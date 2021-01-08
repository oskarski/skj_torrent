package Host.Services;

import Host.HostState;
import Tcp.TcpServer.ServerException;
import utils.FileHasher;
import utils.FileRepository;

import java.io.File;
import java.util.Base64;

public class HostService {
    private final LocalFileService localFileService = new LocalFileService();

    public String getListFilesData() {
        try {
            StringBuilder data = new StringBuilder();

            for (File file : localFileService.getFileList()) {
                String fileHash = FileHasher.getFileHash(file);

                data.append(fileHash).append(" ")
                        .append(file.length()).append(" ")
                        .append(file.getName()).append("\r\n");
            }

            data.append("\r\n\r\n");

            return data.toString();
        } catch (Exception e) {
            throw ServerException.internalServerErrorException();
        }
    }

    public String getPullFileData(String fileHash, int chunk) {
        try {
            File file = localFileService.getFileByHash(fileHash);

            return Base64.getEncoder().encodeToString(FileRepository.readChunk(file, (long) chunk * HostState.chunkSize(), HostState.chunkSize()));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void handlePushFile(String fileName, String fileHash, String fileData, int chunk, int totalChunks) {
        try {
            byte[] fileBytes = Base64.getDecoder().decode(fileData);
            String fileHashDirectoryPathname = HostState.getWorkspacePullTmpPathname() + "/" + fileHash;
            File fileHashDirectory = new File(fileHashDirectoryPathname);

            if (!fileHashDirectory.exists()) fileHashDirectory.mkdirs();

            File chunkFile = new File(fileHashDirectoryPathname + "/" + chunk + ".chunk");

            FileRepository.write(chunkFile, fileBytes);

            if (chunk == totalChunks - 1) {
                localFileService.createOutputFileFromChunks(fileHash, fileName, totalChunks);
                localFileService.deleteDirectory(fileHashDirectory);
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
