package com.smarthome;

import java.util.Date;

public class AirConditioner extends Device {
    private boolean mode;

    /**
     * Constructor to initialize a new Device object with the specified attributes.
     *
     * @param deviceName         the name of the device
     * @param deviceType         the type of the device
     * @param deviceGroup        the group to which the device belongs
     * @param location           the location of the device
     * @param isTurnedOn         the power status of the device
     * @param batteryLevel       the current battery level of the device
     * @param powerConsumption   the power consumption of the device
     * @param maxBatteryCapacity the maximum battery capacity of the device
     * @param powerLevel         the current power level of the device
     */

    private long simulationTempChangeTime;
    AirConditioner(String deviceName, String deviceType, String deviceGroup, String location, Boolean isTurnedOn, double batteryLevel, double powerConsumption, int maxBatteryCapacity, int powerLevel, boolean mode) {
        super(deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel);

        //true = cooling, false = heating
        this.mode = mode;
        this.simulationTempChangeTime = 0;
    }

    public boolean getMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public void toggleMode() {
        this.mode = !this.mode;
    }

    public long getSimulationTempChangeTime() {
        return simulationTempChangeTime;
    }

    public void setSimulationTempChangeTime(long simulationTempChangeTime) {
        this.simulationTempChangeTime = simulationTempChangeTime;
    }

    public int getMinutesSinceTempChange() {
        return (int)  ((new Date().getTime() - simulationTempChangeTime) / 60000);
    }

    @Override
    public String toString() {
        return super.toString() + " Mode: " + (mode ? "Cooling" : "Heating");
    }
}
