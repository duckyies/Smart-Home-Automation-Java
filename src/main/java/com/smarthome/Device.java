package com.smarthome;

public class Device {

    public String deviceName;
    public String deviceType;
    private Boolean isTurnedOn = false;
    private int batteryLevel;
    private int powerConsumption;
    private int batteryConsumption;
    private int batteryCapacity;

    Device(String deviceName, String deviceType, Boolean isTurnedOn, int batteryLevel, int powerConsumption, int batteryConsumption, int batteryCapacity) {
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.isTurnedOn = isTurnedOn;
        this.batteryLevel = batteryLevel;
        this.powerConsumption = powerConsumption;
        this.batteryConsumption = batteryConsumption;
        this.batteryCapacity = batteryCapacity;
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

    public int getBatteryConsumption() {
        return batteryConsumption;
    }

    public void setBatteryConsumption(int batteryConsumption) {
        this.batteryConsumption = batteryConsumption;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
    public int getBatteryCapacity() {
        return batteryCapacity;
    }



}
