package com.smarthome;

import java.util.ArrayList;

public class DeviceType {
    public ArrayList<Device> devices;
    public String typeName;

    DeviceType(String typeName) {
        this.typeName = typeName;
        this.devices = new ArrayList<>();
    };

}
