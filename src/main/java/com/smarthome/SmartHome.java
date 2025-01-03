package com.smarthome;
import java.util.concurrent.*;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SmartHome {


    private ConcurrentHashMap<String, DeviceGroup> groupMap;
    private ConcurrentHashMap<String, DeviceType> typeMap;
    private ArrayList<String> deviceLocationList;

    private int threshold = 2;

    public void initialize() {

        ArrayList<String> deviceGroupList = new ArrayList<>(List.of("Lights", "Fans", "Alarms", "Cameras", "AirConditioners", "Heaters", "Appliances", "Gardening", "Others"));
        ArrayList<String> deviceTypeList = new ArrayList<>(List.of("Decorative", "Necessary", "Health", "Entertainment", "Security", "Others"));

        groupMap = new ConcurrentHashMap<>();
        typeMap = new ConcurrentHashMap<>();

        for (String deviceGroup : deviceGroupList) {
            groupMap.put(deviceGroup, new DeviceGroup(deviceGroup));
        }
        for (String deviceType : deviceTypeList) {
            typeMap.put(deviceType, new DeviceType(deviceType));
        }

        startTick();
    }

    private void startTick() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::tick, 0, 1, TimeUnit.SECONDS);
    }

    private void tick() {
        
    }

    public void  addToGroupAndType(Device device) {
        groupMap.get(device.deviceGroup).addDevice(device);
        typeMap.get(device.deviceType).addDevice(device);
    }

    public Device createDevice(String deviceName, String deviceType, String deviceGroup, String location) {
        return new Device(deviceName, deviceType, deviceGroup, location, false, 0,  0, 0, 0);
    };

    public Device createDevice(String deviceName, String deviceType, String deviceGroup, String location, boolean isTurnedOn, int batteryLevel, int powerConsumption, int maxBatteryCapacity, int powerLevel) {
        return new Device(deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel);
    }

    public void tickTask() {
        //so dont forget you made this
        // you planned to add stuff like adding power consumtion etc in every tick etc etc here
        // all the calculations and stuff yk? dont forget
    }

    //another function i thought of but immediately forgot something to do with tick

    public static void main(String[] args) {

    }
}