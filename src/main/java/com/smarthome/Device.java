package com.smarthome;

/**
 * The Device class represents a smart home device with various attributes such as
 * device name, type, group, location, power status, battery level, and power consumption.
 */
public class Device {

    // Static variable to keep track of the number of devices created
    static int deviceNum = 0;

    // Unique identifier for the device
    private int deviceID;
    // Name of the device
    private String deviceName;
    // Type of the device (e.g., light, thermostat)
    private String deviceType;
    // Location of the device within the home
    private String location;
    // Group to which the device belongs (e.g., living room devices)
    private String deviceGroup;

    // Indicates whether the device is turned on or off
    private Boolean isTurnedOn = false;

    // Current battery level of the device
    private double batteryLevel;
    // Maximum battery capacity of the device
    private int maxBatteryCapacity;
    // Power consumption of the device
    private double basePowerConsumption;
    // Indicates whether the device is running on battery
    private boolean isOnBattery;
    // Current power level of the device
    private int powerLevel;

    private boolean isInteracted = false;
    /**
     * Constructor to initialize a new Device object with the specified attributes.
     *
     * @param deviceName        the name of the device
     * @param deviceType        the type of the device
     * @param deviceGroup       the group to which the device belongs
     * @param location          the location of the device
     * @param isTurnedOn        the power status of the device
     * @param batteryLevel      the current battery level of the device
     * @param powerConsumption  the power consumption of the device
     * @param maxBatteryCapacity the maximum battery capacity of the device
     * @param powerLevel        the current power level of the device
     */
    Device(String deviceName, String deviceType, String deviceGroup, String location, Boolean isTurnedOn, double batteryLevel, double powerConsumption, int maxBatteryCapacity, int powerLevel) {
        this.deviceName = deviceName;
        this.deviceID = deviceNum++;
        this.deviceType = deviceType;
        this.deviceGroup = deviceGroup;
        this.location = location;
        this.isTurnedOn = isTurnedOn;
        this.batteryLevel = batteryLevel;
        this.basePowerConsumption = powerConsumption;
        this.maxBatteryCapacity = maxBatteryCapacity;

        this.isInteracted = false;
        this.isOnBattery = this.maxBatteryCapacity > 0;
        this.powerLevel = powerLevel;
    }

    /**
     * Returns the power status of the device.
     *
     * @return true if the device is turned on, false otherwise
     */
    public Boolean isTurnedOn() {
        return isTurnedOn;
    }

    /**
     * Sets the power status of the device.
     *
     * @param isTurnedOn the new power status of the device
     */
    public void setTurnedOn(Boolean isTurnedOn) {
        this.isTurnedOn = isTurnedOn;
    }

    /**
     * Returns the current battery level of the device.
     *
     * @return the current battery level
     */
    public double getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * Sets the current battery level of the device.
     *
     * @param batteryLevel the new battery level
     */
    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    /**
     * Returns the power consumption of the device.
     *
     * @return the power consumption
     */
    public double getBasePowerConsumption() {
        return basePowerConsumption;
    }

    /**
     * Sets the power consumption of the device.
     *
     * @param basePowerConsumption the new power consumption
     */
    public void setBasePowerConsumption(int basePowerConsumption) {
        this.basePowerConsumption = basePowerConsumption;
    }

    /**
     * Sets the maximum battery capacity of the device.
     *
     * @param batteryCapacity the new maximum battery capacity
     */
    public void setBatteryCapacity(int batteryCapacity) {
        this.maxBatteryCapacity = batteryCapacity;
    }

    /**
     * Returns the maximum battery capacity of the device.
     *
     * @return the maximum battery capacity
     */
    public int getBatteryCapacity() {
        return maxBatteryCapacity;
    }

    /**
     * Returns the unique identifier of the device.
     *
     * @return the device ID
     */
    public int getDeviceID() {
        return deviceID;
    }

    /**
     * Returns the name of the device.
     *
     * @return the device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * Sets the name of the device.
     *
     * @param deviceName the new name of the device
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Returns the type of the device.
     *
     * @return the device type
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the type of the device.
     *
     * @param deviceType the new type of the device
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Returns the location of the device.
     *
     * @return the location of the device
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the device.
     *
     * @param location the new location of the device
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the group to which the device belongs.
     *
     * @return the device group
     */
    public String getDeviceGroup() {
        return deviceGroup;
    }

    /**
     * Sets the group to which the device belongs.
     *
     * @param deviceGroup the new group of the device
     */
    public void setDeviceGroup(String deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    /**
     * Returns the current power level of the device.
     *
     * @return the current power level
     */
    public int getPowerLevel() {
        return powerLevel;
    }

    /**
     * Sets the current power level of the device.
     *
     * @param powerLevel the new power level
     */
    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public boolean getInteraction() {
        return isInteracted;
    }

    public void flipInteractionState() {
        isInteracted = !isInteracted;
    }

}