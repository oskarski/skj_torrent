package utils;

public class Regex {
    public static String ipRegex() {
        return "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}";
    }

    public static String portRegex() {
        return "[0-9]+";
    }

    public static String addressRegex() {
        return ipRegex() + ":" + portRegex();
    }

    public static String groupedAddressRegex() {
        return "^(" + ipRegex() + "):(" + portRegex() + ")$";
    }

    public static String fileHashRegex() {
        return "[a-zA-Z0-9]+";
    }

    public static String fileNameRegex() {
        return "[a-zA-Z0-9._-]+";
    }

    public static String fileSizeRegex() {
        return "[0-9]+";
    }

    public static String groupedListFilesDataLineRegex() {
        return "^(" + fileHashRegex() + ") (" + fileSizeRegex() + ") (" + (fileNameRegex()) + ")$";
    }

    public static String numberOfChunksRegex() {
        return "[0-9]+";
    }
}
