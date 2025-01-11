package com.smarthome.enums;

import com.smarthome.devices.Device;

import java.util.ArrayList;

/**
 * The DeviceGroup class represents a group of smart home devices.
 */
public class DeviceGroup {

    public enum DeviceGroupEnum {
        LIGHTS,
        FANS,
        ALARMS,
        CAMERAS,
        AIR_CONDITIONERS,
        HEATERS,
        APPLIANCES,
        GARDENING,
        ENTERTAINMENT,
        CLEANING,
        LAUNDRY,
        WEARABLES,
        BATHROOM,
        OTHERS;
    }

    private ArrayList<Device> devices;
    public String groupName;

    /**
     * Constructor to initialize a new DeviceGroup object with the specified group name.
     *
     * @param groupName the name of the device group
     */
    public DeviceGroup(String groupName) {
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
}