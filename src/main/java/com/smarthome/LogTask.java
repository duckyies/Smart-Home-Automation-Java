package com.smarthome;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogTask implements Comparable<LogTask>{

    private Level logLevel;
    private String message;

    public LogTask(Level logLevel, String message) {
        this.logLevel = logLevel;
        this.message = message;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int compareTo(LogTask task) {
        ArrayList<Level> levelList = new ArrayList<>(List.of(Level.OFF,Level.SEVERE,Level.WARNING,Level.INFO,Level.CONFIG,Level.FINE,Level.FINER, Level.FINEST, Level.ALL));
        int taskLevel = levelList.indexOf(task.getLogLevel());
        int currTaskLevel = levelList.indexOf(getLogLevel());

        if(taskLevel > currTaskLevel) {
            return 1;
        } else if (taskLevel < currTaskLevel) {
            return -1;
        }
        return 0;
    }
}
