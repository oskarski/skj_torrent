package Host;

public class HostMethod {
    public static final String LIST_FILES = "LIST_FILES";

    public static boolean isValidMethod(String maybeMethod) {
        return maybeMethod.equals(LIST_FILES);
    }
}
