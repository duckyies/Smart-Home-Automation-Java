package com.smarthome;

import java.util.ArrayList;

/**
 * The DeviceType class represents a type of smart home devices.
 */
public class DeviceType {
    // List of devices of this type
    private ArrayList<Device> devices;
    // Name of the device type
    public String typeName;

    /**
     * Constructor to initialize a new DeviceType object with the specified type name.
     *
     * @param typeName the name of the device type
     */
    DeviceType(String typeName) {
        this.typeName = typeName;
        this.devices = new ArrayList<>();
    }

    /**
     * Adds a device to the type.
     *
     * @param device the device to be added
     */
    public void addDevice(Device device) {
        this.devices.add(device);
    }

    /**
     * Removes a device from the type.
     *
     * @param device the device to be removed
     */
    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    /**
     * Returns the list of devices of this type.
     *
     * @return the list of devices
     */
    public ArrayList<Device> getDevices() {
        return devices;
    }

    /**
     * Turns off all devices of this type.
     */
    public void turnOffAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(false);
        }
    }

    /**
     * Turns on all devices of this type.
     */
    public void turnOnAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(true);
        }
    }
}