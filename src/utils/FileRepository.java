package utils;

import java.io.*;

public class FileRepository {
    public static byte[] readChunk(File file, long skip, long limit) throws IOException {
        InputStream inStream = new FileInputStream(file);

        long fileTotalSize = file.length();
        long bytesToRead = Math.min(limit, fileTotalSize - skip);

        byte[] fileContentBytes = new byte[Math.toIntExact(bytesToRead)];

        inStream.skip(skip);
        inStream.read(fileContentBytes);
        inStream.close();

        return fileContentBytes;
    }

    public static byte[] read(File file) throws IOException {
        InputStream inStream = new FileInputStream(file);

        byte[] fileContentBytes = new byte[(int) file.length()];
        inStream.read(fileContentBytes);

        inStream.close();

        return fileContentBytes;
    }

    public static void clearFile(File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);

        outputStream.write("".getBytes());
        outputStream.close();
    }

    public static void append(File file, byte[] fileContentBytes) throws IOException {
        OutputStream outputStream = new FileOutputStream(file, true);

        outputStream.write(fileContentBytes);
        outputStream.close();
    }

    public static void write(File file, byte[] fileContentBytes) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);

        outputStream.write(fileContentBytes);
        outputStream.close();
    }
}
