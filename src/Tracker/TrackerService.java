package Tracker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TrackerService {
    public void upsertTorrent(String torrentHash, String address, int numberOfChunks) throws TrackerException {
        HashMap<String, Integer> torrent = this.getTorrent(torrentHash);

        torrent.put(address, numberOfChunks);

        this.saveTorrent(torrent, torrentHash);
    }

    public void deleteFromTorrent(String torrentHash, String address) throws TrackerException {
        HashMap<String, Integer> torrent = this.getTorrent(torrentHash);

        torrent.remove(address);

        this.saveTorrent(torrent, torrentHash);
    }

    public String getTorrentData(String torrentHash) throws TrackerException {
        HashMap<String, Integer> torrent = this.getTorrent(torrentHash);
        StringBuilder data = new StringBuilder();

        for (Map.Entry<String, Integer> entry : torrent.entrySet()) {
            String address = entry.getKey();
            Integer numberOfChunks = entry.getValue();

            data.append(address).append("/").append(numberOfChunks).append("\r\n");
        }

        data.append("\r\n\r\n");

        return data.toString();
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

        if (torrent.isEmpty()) {
            if (!file.delete()) throw TrackerException.internalServerErrorException();

            return;
        }

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
