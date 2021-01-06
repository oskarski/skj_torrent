package HostTracker;

import Server.ServerException;

import java.io.*;
import java.util.HashSet;

public class HostTrackerService {
    public void registerHost(String hostAddress) {
        HashSet<String> hosts = this.getHosts();

        hosts.add(hostAddress);

        this.saveHosts(hosts);
    }

    public void unregisterHost(String hostAddress) {
        HashSet<String> hosts = this.getHosts();

        hosts.remove(hostAddress);

        this.saveHosts(hosts);
    }

    public String getHostsData() {
        HashSet<String> hosts = this.getHosts();

        StringBuilder data = new StringBuilder();

        for (String host : hosts) {
            data.append(host).append("\r\n");
        }

        data.append("\r\n\r\n");

        return data.toString();
    }

    private HashSet<String> getHosts() {
        File file = new File(HostTrackerState.getHostsPathname());
        HashSet<String> hosts = new HashSet<>();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            hosts = (HashSet<String>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException exception) {
            throw ServerException.internalServerErrorException();
        }

        return hosts;
    }

    private void saveHosts(HashSet<String> hosts) {
        File file = new File(HostTrackerState.getHostsPathname());

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(hosts);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException exception) {
            throw ServerException.internalServerErrorException();
        }
    }
}
