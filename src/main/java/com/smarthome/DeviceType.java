package com.smarthome;

import java.util.ArrayList;

public class DeviceType {
    private ArrayList<Device> devices;
    public String typeName;

    DeviceType(String typeName) {
        this.typeName = typeName;
        this.devices = new ArrayList<>();
    };

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void turnOffAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(false);
        }
    }

    public void turnOnAllDevices() {
        for (Device device : devices) {
            device.setTurnedOn(true);
        }
    }

}
