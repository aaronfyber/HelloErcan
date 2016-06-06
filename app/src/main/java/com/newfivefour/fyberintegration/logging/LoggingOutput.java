package com.newfivefour.fyberintegration.logging;

public interface LoggingOutput {
    void debugOutput(String text);
    void warningOutput(String text);
    void errorOutput(String text);
    void fillLog(String log);
    void clearLog();
}
