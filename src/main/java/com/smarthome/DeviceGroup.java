package com.smarthome;

import java.util.ArrayList;

/**
 * The DeviceGroup class represents a group of smart home devices.
 */
public class DeviceGroup {
    // List of devices in the group
    private ArrayList<Device> devices;
    // Name of the device group
    public String groupName;

    /**
     * Constructor to initialize a new DeviceGroup object with the specified group name.
     *
     * @param groupName the name of the device group
     */
    DeviceGroup(String groupName) {
        this.groupName = groupName;
        this.devices = new ArrayList<>();
    }

    /**
     * Adds a device to the group.
     *
     * @param device the device to be added
     */
    public void addDevice(Device device) {
        this.devices.add(device);
    }

    /**
     * Removes a device from the group.
     *
     * @param device the device to be removed
     */
    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    /**
     * Returns the list of devices in the group.
     *
     * @return the list of devices
     */
    public ArrayList<Device> getDevices() {
        return devices;
    }

    /**
     * Turns off all devices in the group.
     */
    public void turnOffAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(false);
        }
    }

    /**
     * Turns on all devices in the group.
     */
    public void turnOnAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(true);
        }
    }
}