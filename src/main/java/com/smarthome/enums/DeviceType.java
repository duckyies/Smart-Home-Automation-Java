package com.smarthome.enums;

import com.smarthome.devices.Device;

import java.util.ArrayList;

/**
 * The DeviceType class represents a type of smart home devices.
 */
public class DeviceType {

    public enum DeviceTypeEnum {
        DECORATIVE,
        NECESSARY,
        HEALTH,
        ENTERTAINMENT,
        SECURITY,
        PERSONALCARE,
        CONNECTIVITY,
        COOKING,
        LUXURY,
        OFFICE,
        OTHERS;
    }

    private ArrayList<Device> devices;
    public String typeName;

    /**
     * Constructor to initialize a new DeviceType object with the specified type name.
     *
     * @param typeName the name of the device type
     */
    public DeviceType(String typeName) {
        this.typeName = typeName;
        this.devices = new ArrayList<>();
    }

    public String getTypeName() {
        return typeName;
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