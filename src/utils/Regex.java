package utils;

public class Regex {
    public static String ipRegex() {
        return digits(1, 3) + "[.]" + digits(1, 3) + "[.]" + digits(1, 3) + "[.]" + digits(1, 3);
    }

    public static String addressRegex() {
        return ipRegex() + ":" + digits();
    }

    public static String groupedAddressRegex() {
        return "^(" + ipRegex() + "):(" + digits() + ")$";
    }

    public static String fileHashRegex() {
        return "[a-zA-Z0-9]+";
    }

    public static String fileNameRegex() {
        return "[a-zA-Z0-9._-]+";
    }

    public static String digit() {
        return "[0-9]";
    }

    public static String digits() {
        return digit() + "+";
    }

    public static String digits(int min, int max) {
        return digit() + "{" + min + "," + max + "}";
    }
}
