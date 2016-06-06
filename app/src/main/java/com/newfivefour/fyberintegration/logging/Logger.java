package com.newfivefour.fyberintegration.logging;

public class Logger {

    static private LoggingOutput loggingInterface = new LoggingOutput() {
        @Override public void debugOutput(String tag, String text) {}
        @Override public void warningOutput(String tag, String text) {}
        @Override public void errorOutput(String tag, String text) {}
    };

    private Logger() {}

    public static void init(LoggingOutput output) {
        loggingInterface = output;
    }

    public static void debugOutput(String tag, String text) {
        loggingInterface.debugOutput(tag, text);
    }

    public static void warningOutput(String tag, String text) {
        loggingInterface.warningOutput(tag, text);
    }

    public static void errorOutput(String tag, String text) {
        loggingInterface.errorOutput(tag, text);
    }
}
