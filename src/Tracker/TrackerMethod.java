package Tracker;

public class TrackerMethod {
    public static final String T_GET = "T_GET";
    public static final String T_POST = "T_POST";
    public static final String T_PUT = "T_PUT";
    public static final String T_DELETE = "T_DELETE";

    public static boolean isValidMethod(String maybeMethod) {
        return maybeMethod.equals(T_GET) || maybeMethod.equals(T_POST) ||
                maybeMethod.equals(T_PUT) || maybeMethod.equals(T_DELETE);
    }
}
