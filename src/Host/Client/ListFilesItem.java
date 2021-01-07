package Host.Client;

public class ListFilesItem {
    public final String fileHash;
    public final int size;
    public final String fileName;

    public ListFilesItem(String fileHash, int size, String fileName) {
        this.fileHash = fileHash;
        this.size = size;
        this.fileName = fileName;
    }
}
