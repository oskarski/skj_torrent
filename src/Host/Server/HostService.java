package Host.Server;

import Host.HostState;
import Host.LocalFileService;
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
            File file = this.localFileService.getFileByHash(fileHash);

            return Base64.getEncoder().encodeToString(FileRepository.readChunk(file, (long) chunk * HostState.chunkSize(), HostState.chunkSize()));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
