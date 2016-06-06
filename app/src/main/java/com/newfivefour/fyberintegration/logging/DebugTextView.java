package com.newfivefour.fyberintegration.logging;

import android.widget.TextView;

public class DebugTextView implements LoggingOutput {

    private TextView tv;

    public DebugTextView(TextView tv) {
        this.tv = tv;
    }

    @Override
    public void debugOutput(String s) {
        this.tv.append(s);
    }

    @Override
    public void warningOutput(String s) {
        this.tv.append(s);
    }

    @Override
    public void errorOutput(String s) {
        this.tv.append(s);
    }

    @Override
    public void fillLog(String log) {
        this.tv.setText(log);
    }

    @Override
    public void clearLog() {
        this.tv.setText("");
    }
}
