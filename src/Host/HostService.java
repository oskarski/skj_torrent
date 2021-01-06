package Host;

import java.io.File;

public class HostService {
    public String getListFilesData() {
        File directory = new File(HostState.getWorkspacePathname());

        StringBuilder data = new StringBuilder();

        for (File file : directory.listFiles()) {
            data.append(file.getName()).append(" ")
                    // todo send file hashes
                    .append("HASH_TODO").append(" ")
                    .append(file.length()).append("\r\n");
        }

        data.append("\r\n\r\n");

        return data.toString();
    }
}
