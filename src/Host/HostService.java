package Host;

import Server.ServerException;

import java.io.File;

public class HostService {
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
}
