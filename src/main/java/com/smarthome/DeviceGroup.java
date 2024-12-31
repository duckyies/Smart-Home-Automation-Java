package com.smarthome;

import java.util.ArrayList;

public class DeviceGroup {
    public ArrayList<Device> devices;
    public String groupName;

    DeviceGroup(String groupName) {
        this.groupName = groupName;
        this.devices = new ArrayList<>();
    };

}
