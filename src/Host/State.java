package Host;

public class State {
    private static boolean isHostRunning;
    private static String workspacePathname;

    public static void init(String workspacePathname) {
        State.workspacePathname = workspacePathname;
        State.isHostRunning = true;
    }

    public static String getWorkspacePathname() {
        return workspacePathname;
    }

    public static boolean getIsHostRunning() {
        return isHostRunning;
    }

    public static void setIsHostRunning(boolean isHostRunning) {
        State.isHostRunning = isHostRunning;
    }
}
