package com.smarthome;

public class Rule {

    private int deviceId;
    private boolean flipState;
    private boolean setPowerLevel;
    private int powerLevel;


    Rule(int deviceId, boolean flipState, boolean setPowerLevel, int powerLevel) {
        this.deviceId = deviceId;
        this.flipState = flipState;
        this.setPowerLevel = setPowerLevel;
        this.powerLevel = powerLevel;
    }
}
