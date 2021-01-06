package HostTracker;

public class HostTrackerMethod {
    public static final String LIST_HOSTS = "LIST_HOSTS";
    public static final String REGISTER_HOST = "REGISTER_HOST";
    public static final String UNREGISTER_HOST = "UNREGISTER_HOST";

    public static boolean isValidMethod(String maybeMethod) {
        return maybeMethod.equals(LIST_HOSTS) || maybeMethod.equals(REGISTER_HOST) || maybeMethod.equals(UNREGISTER_HOST);
    }
}
