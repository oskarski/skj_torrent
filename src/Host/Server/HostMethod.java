package Host.Server;

public class HostMethod {
    public static final String LIST_FILES = "LIST_FILES";
    public static final String PING = "PING";
    public static final String PULL_FILE = "PULL_FILE";

    public static boolean isValidMethod(String maybeMethod) {
        return maybeMethod.equals(LIST_FILES) || maybeMethod.equals(PING) ||
                maybeMethod.equals(PULL_FILE);
    }
}
