package Host;

import Server.ServerException;
import utils.io.FileRepository;

import java.io.File;
import java.util.Base64;

public class HostService {
    private final FileService fileService = new FileService();

    public String getListFilesData() {
        try {
            File directory = new File(HostState.getWorkspacePathname());

            StringBuilder data = new StringBuilder();

            for (File file : directory.listFiles()) {
                data.append(FileHasher.getFileHash(file)).append(" ")
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
            File file = this.fileService.getFileByHash(fileHash);

            return Base64.getEncoder().encodeToString(FileRepository.readChunk(file, (long) chunk * HostState.chunkSize(), HostState.chunkSize()));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
