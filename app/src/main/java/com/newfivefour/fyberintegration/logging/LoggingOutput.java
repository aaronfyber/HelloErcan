package com.newfivefour.fyberintegration.logging;

public interface LoggingOutput {
    void debugOutput(String tag, String text);
    void warningOutput(String tag, String text);
    void errorOutput(String tag, String text);
}
