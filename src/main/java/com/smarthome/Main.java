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
    private HashMap<String, Integer> typeMap;
    private ArrayList<String> deviceGroupList;
    private ArrayList<String> deviceTypeList;

    public void initialize() {

        deviceGroupList = new ArrayList<>(List.of("Lights", "Fans", "Alarms", "Cameras", "AirConditioners", "Heaters", "Appliances", "Gardening", "Others"));
        deviceTypeList = new ArrayList<>(List.of("Decorative", "Necessary", "Health", "Entertainment", "Security"));

        groupMap = new HashMap<>();
        typeMap = new HashMap<>();

        for (String deviceGroup : deviceGroupList) {
            groupMap.put(deviceGroup, new DeviceGroup(deviceGroup));
        }
        for (String deviceType : deviceTypeList) {
            typeMap.put(deviceType, 0);
        }


        .
    }

    public Device addDevice(String deviceName) {

    };

    public static void main(String[] args) {

    }
}