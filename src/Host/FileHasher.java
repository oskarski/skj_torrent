package Host;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHasher {
    public static String getFileHash(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        FileInputStream fileInputStream = new FileInputStream(file);

        int chunkSize = 1024;
        byte[] byteArray = new byte[chunkSize];
        int bytesCount = 0;

        while ((bytesCount = fileInputStream.read(byteArray)) != -1) {
            messageDigest.update(byteArray, 0, bytesCount);
        }

        fileInputStream.close();

        StringBuilder hash = new StringBuilder();

        for (byte fileByte : messageDigest.digest()) {
            hash.append(Integer.toString((fileByte & 0xff) + 0x100, 16).substring(1));
        }

        return hash.toString();
    }
}
