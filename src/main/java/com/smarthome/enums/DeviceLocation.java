package com.smarthome.enums;

import com.smarthome.devices.Device;

import java.util.ArrayList;

/**
 * The DeviceLocation class represents a location within a smart home that contains multiple devices.
 */
public class DeviceLocation {

    public enum DeviceLocationEnum {
        LIVING_ROOM,
        BEDROOM,
        BEDROOM2,
        BEDROOM3,
        BEDROOM4,
        GARDEN,
        OFFICE,
        ENTRANCE,
        KITCHEN,
        BATHROOM,
        BATHROOM2,
        BATHROOM3,
        OTHERS;
    }

    private ArrayList<Device> devices;
    public String location;

    private double temperature;

    /**
     * Constructor to initialize a new DeviceLocation object with the specified location name.
     *
     * @param location the name of the location
     */
    public DeviceLocation(String location) {
        this.location = location;
        this.devices = new ArrayList<>();
    }

    /**
     * Adds a device to the location.
     *
     * @param device the device to be added
     */
    public void addDevice(Device device) {
        this.devices.add(device);
    }

    /**
     * Removes a device from the location.
     *
     * @param device the device to be removed
     */
    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    /**
     * Returns the list of devices in the location.
     *
     * @return the list of devices
     */
    public ArrayList<Device> getDevices() {
        return devices;
    }

    /**
     * Turns off all devices in the location.
     */
    public void turnOffAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(false);
        }
    }

    public String getLocation() {
        return location;
    }

    /**
     * Turns on all devices in the location.
     */
    public void turnOnAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(true);
        }
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Device getDeviceByName(String name) {
        return devices.stream()
                .filter(device -> device.getDeviceName()
                .toLowerCase()
                .contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public Device getDeviceById(int id) {
        return devices.stream().filter(device -> device.getDeviceID() == id).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Location: " + location;
    }
}