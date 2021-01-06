package Tracker;

import java.io.*;
import java.util.HashMap;

public class TrackerService {
    public void register(String torrentHash, String address, int numberOfChunks) throws TrackerException {
        HashMap<String, Integer> torrent = this.getTorrent(torrentHash);

        torrent.put(address, numberOfChunks);

        this.saveTorrent(torrent, torrentHash);
    }

    private File getTorrentFile(String torrentHash) {
        return new File("src/Tracker/data/" + torrentHash + ".track");
    }

    private HashMap<String, Integer> getTorrent(String torrentHash) throws TrackerException {
        File file = this.getTorrentFile(torrentHash);
        HashMap<String, Integer> torrent = new HashMap<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            torrent = (HashMap<String, Integer>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException exception) {
            throw TrackerException.internalServerErrorException();
        }

        return torrent;
    }

    private void saveTorrent(HashMap<String, Integer> torrent, String torrentHash) throws TrackerException {
        File file = this.getTorrentFile(torrentHash);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(torrent);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException exception) {
            throw TrackerException.internalServerErrorException();
        }
    }
}
