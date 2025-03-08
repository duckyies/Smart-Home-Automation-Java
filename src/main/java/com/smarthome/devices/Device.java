package com.smarthome.devices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smarthome.enums.DeviceGroup.DeviceGroupEnum;
import com.smarthome.enums.DeviceLocation.DeviceLocationEnum;
import com.smarthome.enums.DeviceType.DeviceTypeEnum;
import jakarta.persistence.Id;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * The Device class represents a smart home device with various attributes such as
 * device name, type, group, location, power status, battery level, and power consumption.
 */

@Document(collection = "devices")
@Component
public class Device {

    static int deviceNum = 0;
    private final Date date = new Date();

    @Id
    private String id;

    @Field("deviceID")
    private int deviceID;

    @Field("deviceName")
    @JsonProperty("deviceName")
    private String deviceName;

    @Field("deviceType")
    @JsonProperty("deviceType")
    private DeviceTypeEnum deviceType;

    @Field("location")
    @JsonProperty("location")
    private DeviceLocationEnum location;

    @Field("deviceGroup")
    @JsonProperty("deviceGroup")
    private DeviceGroupEnum deviceGroup;

    @Field("batteryLevel")
    @JsonProperty("batteryLevel")
    private double batteryLevel;

    @Field("maxBatteryCapacity")
    @JsonProperty("maxBatteryCapacity")
    private int maxBatteryCapacity;

    @Field("currentBatteryCapacity")
    @JsonProperty("currentBatteryCapacity")
    private double currentBatteryCapacity;

    @Field("isOnBattery")
    @JsonProperty("isOnBattery")
    private boolean isOnBattery;

    @Field("isTurnedOn")
    @JsonProperty("isTurnedOn")
    private Boolean isTurnedOn = false; 

    @Field("basePowerConsumption")
    @JsonProperty("basePowerConsumption")
    private double basePowerConsumption;

    @Field("powerLevel")
    @JsonProperty("powerLevel")
    private int powerLevel;

    @Field("turnedOnTime")
    @JsonProperty("turnedOnTime")
    private long turnedOnTime;

    @Field("isInteracted")
    @JsonProperty("isInteracted")
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
    @Autowired
    public Device(String deviceName, DeviceTypeEnum deviceType, DeviceGroupEnum deviceGroup, DeviceLocationEnum location, Boolean isTurnedOn, double batteryLevel, double powerConsumption, int maxBatteryCapacity, int powerLevel) {
        this.deviceName = deviceName;
        this.deviceID = deviceNum++;
        this.deviceType = deviceType;
        this.deviceGroup = deviceGroup;
        this.location = location;
        this.isTurnedOn = isTurnedOn;
        this.batteryLevel = batteryLevel;
        this.basePowerConsumption = powerConsumption;
        this.maxBatteryCapacity = maxBatteryCapacity;
        this.currentBatteryCapacity = maxBatteryCapacity;
        this.isInteracted = false;
        this.isOnBattery = this.maxBatteryCapacity > 0;
        this.powerLevel = powerLevel;
    }

    public Device() {
        this.deviceID = deviceNum++;
    }

    public Device(String name, DeviceTypeEnum type, DeviceGroupEnum group, DeviceLocationEnum location, Boolean status) {
    }

    public Device(String name, DeviceTypeEnum type, DeviceGroupEnum group, DeviceLocationEnum location) {
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
    public void setBatteryLevel(double batteryLevel) {
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
    public void setBasePowerConsumption(double basePowerConsumption) {
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
    public DeviceTypeEnum getDeviceType() {
        return deviceType;
    }

    /**
     * Sets the type of the device.
     *
     * @param deviceType the new type of the device
     */
    public void setDeviceType(DeviceTypeEnum deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Returns the location of the device.
     *
     * @return the location of the device
     */
    public DeviceLocationEnum getLocation() {
        return location;
    }

    /**
     * Sets the location of the device.
     *
     * @param location the new location of the device
     */
    public void setLocation(DeviceLocationEnum location) {
        this.location = location;
    }

    /**
     * Returns the group to which the device belongs.
     *
     * @return the device group
     */
    public DeviceGroupEnum getDeviceGroup() {
        return deviceGroup;
    }

    /**
     * Sets the group to which the device belongs.
     *
     * @param deviceGroup the new group of the device
     */
    public void setDeviceGroup(DeviceGroupEnum deviceGroup) {
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

    public boolean isOnBattery() {
        return isOnBattery;
    }

    public void setOnBattery(boolean isOnBattery) {
        this.isOnBattery = isOnBattery;
    }

    public double getCurrentBatteryCapacity() {
        return currentBatteryCapacity;
    }

    public void setCurrentBatteryCapacity(double capacity) {
        currentBatteryCapacity = capacity;
    }

    public double getMaxBatteryCapacity() {
        return maxBatteryCapacity;
    }

    public void setTurnedOnTime(long time) {
        turnedOnTime = time;
    }

    public long getTurnedOnTime() {
        return turnedOnTime;
    }

    public int getMinutesSinceTurnedOn() {
        return (int) ((new Date().getTime() - turnedOnTime) / 60000);
    }

    public Date getDate() {
        return date;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    public String toString() {
        return "Device ID: " + deviceID + "\n" +
                "Device Name: " + deviceName + "\n" +
                "Device Type: " + deviceType + "\n" +
                "Device Group: " + deviceGroup + "\n" +
                "Location: " + location + "\n" +
                "Power Status: " + (isTurnedOn ? "On" : "Off") + "\n" +
                "Battery Level: " + batteryLevel + "\n" +
                "Power Consumption: " + basePowerConsumption + " W\n" +
                "Power Level: " + powerLevel + "\n";
    }

}