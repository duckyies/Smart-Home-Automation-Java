package com.smarthome;

import java.util.ArrayList;

/**
 * The DeviceLocation class represents a location within a smart home that contains multiple devices.
 */
public class DeviceLocation {
    // List of devices in the location
    private ArrayList<Device> devices;
    // Name of the location
    public String location;

    /**
     * Constructor to initialize a new DeviceLocation object with the specified location name.
     *
     * @param location the name of the location
     */
    DeviceLocation(String location) {
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

    /**
     * Turns on all devices in the location.
     */
    public void turnOnAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(true);
        }
    }
}