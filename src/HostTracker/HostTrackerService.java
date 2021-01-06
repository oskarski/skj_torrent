package HostTracker;

import java.io.*;
import java.util.HashSet;

public class HostTrackerService {
    public void registerHost(String hostAddress) throws HostTrackerException {
        HashSet<String> hosts = this.getHosts();

        hosts.add(hostAddress);

        this.saveHosts(hosts);
    }

    public void unregisterHost(String hostAddress) throws HostTrackerException {
        HashSet<String> hosts = this.getHosts();

        hosts.remove(hostAddress);

        this.saveHosts(hosts);
    }

    public String getHostsData() throws HostTrackerException {
        HashSet<String> hosts = this.getHosts();

        StringBuilder data = new StringBuilder();

        for (String host : hosts) {
            data.append(host).append("\r\n");
        }

        data.append("\r\n\r\n");

        return data.toString();
    }

    private File getHostsFile() {
        return new File("src/HostTracker/hosts.txt");
    }

    private HashSet<String> getHosts() throws HostTrackerException {
        File file = this.getHostsFile();
        HashSet<String> hosts = new HashSet<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            hosts = (HashSet<String>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException exception) {
            throw HostTrackerException.internalServerErrorException();
        }

        return hosts;
    }

    private void saveHosts(HashSet<String> hosts) throws HostTrackerException {
        File file = this.getHostsFile();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(hosts);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException exception) {
            throw HostTrackerException.internalServerErrorException();
        }
    }
}
