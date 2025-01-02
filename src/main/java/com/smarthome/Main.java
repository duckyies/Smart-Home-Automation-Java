package com.smarthome;

/**
 * Entry point for the Smart Home Automation project.
 * Automates smart home devices using data structures
 *
 * @author Abhinav Variyath
 * @author Anirudh Jayan
 * @author Tara Samiksha
 * @author Sarvesh Ram Kumar
 * @version 1.0
 * @date 2024-12-27
 */

import com.smarthome.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {


    private HashMap<String, DeviceGroup> groupMap;
    private HashMap<String, DeviceType> typeMap;
    private ArrayList<String> deviceGroupList;
    private ArrayList<String> deviceTypeList;

    private int threshold =

    public void initialize() {

        deviceGroupList = new ArrayList<>(List.of("Lights", "Fans", "Alarms", "Cameras", "AirConditioners", "Heaters", "Appliances", "Gardening", "Others"));
        deviceTypeList = new ArrayList<>(List.of("Decorative", "Necessary", "Health", "Entertainment", "Security", "Others"));

        groupMap = new HashMap<>();
        typeMap = new HashMap<>();

        for (String deviceGroup : deviceGroupList) {
            groupMap.put(deviceGroup, new DeviceGroup(deviceGroup));
        }
        for (String deviceType : deviceTypeList) {
            typeMap.put(deviceType, new DeviceType(deviceType));
        }
    }

    public void  addToGroupAndType(Device device) {
        groupMap.get(device.deviceGroup).addDevice(device);
        typeMap.get(device.deviceType).addDevice(device);
    }

    public Device createDevice(String deviceName, String deviceType, String deviceGroup, String location) {
        return new Device(deviceName, deviceType, deviceGroup, location, false, 0, 0, 0, 0);
    };

    public Device createDevice(String deviceName, String deviceType, String deviceGroup, String location, boolean isTurnedOn, int batteryLevel, int powerConsumption, int batteryConsumption, int batteryCapacity) {
        return new Device(deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, batteryConsumption, batteryCapacity);
    }



    public static void main(String[] args) {

    }
}