package utils;

public class Regex {
    public static String addressRegex() {
        return "[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}:[0-9]+";
    }

    public static String numberOfChunksRegex() {
        return "[0-9]+";
    }
}
