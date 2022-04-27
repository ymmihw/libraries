package com.ymmihw.libraries.polymorphism;

public class JsonStringFormatterUtil {
    public static String formatJson(String input) {
        return input.replaceAll("'", "\"");
    }
}
