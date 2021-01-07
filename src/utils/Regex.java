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

    public static String numberOfChunksRegex() {
        return "[0-9]+";
    }
}
