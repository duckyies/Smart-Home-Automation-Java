package com.smarthome;

public class Rule {

    private int deviceId;
    private boolean flipState;
    private boolean setPowerLevel;
    private int powerLevel;
    private boolean turnGroupOff;
    private boolean turnGroupOn;
    private String groupName;
    private boolean turnTypeOff;
    private boolean turnTypeOn;
    private String typeName;
    private boolean turnLocationOff;
    private boolean turnLocationOn;
    private String locationName;

    public Rule(int deviceId, boolean flipState, boolean setPowerLevel, int powerLevel, boolean turnGroupOff, boolean turnGroupOn, String groupName, boolean turnTypeOff, boolean turnTypeOn, String typeName, boolean turnLocationOff, boolean turnLocationOn, String locationName) {
        this.deviceId = deviceId;
        this.flipState = flipState;
        this.setPowerLevel = setPowerLevel;
        this.powerLevel = powerLevel;
        this.turnGroupOff = turnGroupOff;
        this.turnGroupOn = turnGroupOn;
        this.groupName = groupName;
        this.turnTypeOff = turnTypeOff;
        this.turnTypeOn = turnTypeOn;
        this.typeName = typeName;
        this.turnLocationOff = turnLocationOff;
        this.turnLocationOn = turnLocationOn;
        this.locationName = locationName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isFlipState() {
        return flipState;
    }

    public void setFlipState(boolean flipState) {
        this.flipState = flipState;
    }

    public boolean isSetPowerLevel() {
        return setPowerLevel;
    }

    public void setSetPowerLevel(boolean setPowerLevel) {
        this.setPowerLevel = setPowerLevel;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public boolean isTurnGroupOff() {
        return turnGroupOff;
    }

    public void setTurnGroupOff(boolean turnGroupOff) {
        this.turnGroupOff = turnGroupOff;
    }

    public boolean isTurnGroupOn() {
        return turnGroupOn;
    }

    public void setTurnGroupOn(boolean turnGroupOn) {
        this.turnGroupOn = turnGroupOn;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isTurnTypeOff() {
        return turnTypeOff;
    }

    public void setTurnTypeOff(boolean turnTypeOff) {
        this.turnTypeOff = turnTypeOff;
    }

    public boolean isTurnTypeOn() {
        return turnTypeOn;
    }

    public void setTurnTypeOn(boolean turnTypeOn) {
        this.turnTypeOn = turnTypeOn;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isTurnLocationOff() {
        return turnLocationOff;
    }

    public void setTurnLocationOff(boolean turnLocationOff) {
        this.turnLocationOff = turnLocationOff;
    }

    public boolean isTurnLocationOn() {
        return turnLocationOn;
    }

    public void setTurnLocationOn(boolean turnLocationOn) {
        this.turnLocationOn = turnLocationOn;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
}
