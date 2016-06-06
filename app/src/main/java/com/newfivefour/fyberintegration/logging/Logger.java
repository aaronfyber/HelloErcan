package com.newfivefour.fyberintegration.logging;

import android.util.Log;

public class Logger {

    static private LoggingOutput loggingInterface = new LoggingOutput() {
        @Override public void debugOutput(String text) {}
        @Override public void warningOutput(String text) {}
        @Override public void errorOutput(String text) {}
        @Override public void fillLog(String log) {}
        @Override public void clearLog() {} };

    static private String previousLog = "";

    private Logger() {}

    public static void init(LoggingOutput output) {
        loggingInterface = output;
        loggingInterface.fillLog(previousLog);
    }

    public static void debugOutput(String tag, String text) {
        String s = "D " + tag + " " + text + "\n";
        previousLog+=s;
        loggingInterface.debugOutput(s);
        Log.d(tag, text);
    }

    public static void warningOutput(String tag, String text) {
        String s = "W " + tag + " " + text + "\n";
        previousLog+=s;
        loggingInterface.warningOutput(s);
        Log.w(tag, text);
    }

    public static void errorOutput(String tag, String text) {
        String s = "E " + tag + " " + text + "\n";
        previousLog+=s;
        loggingInterface.errorOutput(s);
        Log.e(tag, text);
    }
}
