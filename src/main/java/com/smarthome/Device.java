package com.smarthome;

public class Device {

    static int deviceNum = 0;

    private int deviceID;
    public String deviceName;
    public String deviceType;
    public String location;
    public String deviceGroup;
    private Boolean isTurnedOn = false;

    private int batteryLevel;
    private int maxBatteryCapacity;
    private int powerConsumption;
    private boolean isOnBattery;
    private int powerLevel;

    Device(String deviceName, String deviceType, String deviceGroup, String location, Boolean isTurnedOn, int batteryLevel, int powerConsumption, int maxBatteryCapacity, int powerLevel) {
        this.deviceName = deviceName;
        this.deviceID = deviceNum++;
        this.deviceType = deviceType;
        this.deviceGroup = deviceGroup;
        this.location = location;
        this.isTurnedOn = isTurnedOn;
        this.batteryLevel = batteryLevel;
        this.powerConsumption = powerConsumption;
        this.maxBatteryCapacity = maxBatteryCapacity;

        this.isOnBattery = this.maxBatteryCapacity > 0;
        this.powerLevel = powerLevel;
    };

    public Boolean isTurnedOn() {
        return isTurnedOn;
    };

    public void setTurnedOn(Boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.maxBatteryCapacity = batteryCapacity;
    }
    public int getBatteryCapacity() {
        return maxBatteryCapacity;
    }

}
