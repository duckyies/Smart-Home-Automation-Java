package com.smarthome.enums;

import com.smarthome.devices.Device;

import java.util.ArrayList;

/**
 * The DeviceGroup class represents a group of smart home devices.
 */
public class DeviceGroup {

    public enum DeviceGroupEnum {
        LIGHTS(10),
        FANS(9),
        ALARMS(15),
        CAMERAS(14),
        AIRCONDITIONERS(8),
        HEATERS(8),
        APPLIANCES(6),
        GARDENING(3),
        ENTERTAINMENT(2),
        CLEANING(5),
        LAUNDRY(4),
        WEARABLES(7),
        BATHROOM(12),
        OTHERS(1);

        private final int priority;

        DeviceGroupEnum(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
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

    public String getGroupName() {
        return groupName;
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