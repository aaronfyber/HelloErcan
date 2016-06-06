package com.newfivefour.fyberintegration.logging;

import android.widget.TextView;

import com.newfivefour.fyberintegration.logging.LoggingOutput;

public class DebugTextView implements LoggingOutput {

    private TextView tv;

    public DebugTextView(TextView tv) {
        this.tv = tv;
    }

    @Override
    public void debugOutput(String tag, String text) {
        this.tv.append("D " + tag + " " + text + "\n");
    }

    @Override
    public void warningOutput(String tag, String text) {
        this.tv.append("W " + tag + " " + text + "\n");
    }

    @Override
    public void errorOutput(String tag, String text) {
        this.tv.append("E " + tag + " " + text + "\n");

    }
}
