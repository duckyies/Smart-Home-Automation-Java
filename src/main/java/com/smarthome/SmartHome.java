package com.smarthome;
import org.jetbrains.annotations.NotNull;

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
import java.util.logging.*;

public class SmartHome {

    private ConcurrentHashMap<String, DeviceGroup> groupMap;
    private ConcurrentHashMap<String, DeviceType> typeMap;
    private ConcurrentHashMap<String, DeviceLocation> locationMap;
    private ArrayList<Device> poweredOnDevices;
    private ArrayList<Device> poweredOffDevices;
    private ScheduledExecutorService scheduler;
    private Logger logger;

    //device queue, you planned to give priority based on type and group, make enum
    private PriorityQueue<Device> deviceQueue;

    //another queue but then you can also make it so that you can reduce power level and if that makes it go below the threshold, do that before turning it off
    private PriorityQueue<Device> powerReducableDevices;

    private LinkedList<LogTask> loggingList;

    // add rules here and complete the whole list in a tick, add another thread for that maybe? is that really needed? who tf knows? might as well go on with the multithreading 😱
    private LinkedList<Rule> ruleList;

    private double powerConsumption = 1;

    private int threshold = 10;

    public void initialize() {

        ArrayList<String> deviceGroupList = new ArrayList<>(
                List.of("Lights", "Fans", "Alarms", "Cameras", "AirConditioners", "Heaters", "Appliances", "Gardening", "Entertainment", "Cleaning", "Laundry", "Wearables","Bathroom", "Others")
        );
        ArrayList<String> deviceTypeList = new ArrayList<>(
                List.of("Decorative", "Necessary", "Health", "Entertainment", "Security", "PersonalCare","Connectivity", "Cooking", "Luxury", "Office", "Others")
        );
        ArrayList<String> locationList = new ArrayList<>(
                List.of("Living Room", "Bedroom", "Bedroom2", "Bedroom3", "Bedroom4", "Garden", "Office", "Entrance", "Kitchen", "Bathroom", "Bathroom2", "Bathroom3", "Others")
        );

        locationMap = new ConcurrentHashMap<>();
        groupMap = new ConcurrentHashMap<>();
        typeMap = new ConcurrentHashMap<>();

        for (String deviceGroup : deviceGroupList) {
            groupMap.put(deviceGroup, new DeviceGroup(deviceGroup));
        }
        for (String deviceType : deviceTypeList) {
            typeMap.put(deviceType, new DeviceType(deviceType));
        }
        for (String location:  locationList) {
            locationMap.put(location, new DeviceLocation(location));
        }

        loggingList = new LinkedList<LogTask>();
        poweredOnDevices = new ArrayList<Device>();
        poweredOffDevices = new ArrayList<Device>();
        ruleList = new LinkedList<Rule>();
        deviceQueue = new PriorityQueue<Device>();
        powerReducableDevices = new PriorityQueue<Device>();
        initializeScheduler();

    }

    private void initializeLogger() {

        //MAKE MULTIPLE
        //SEVERITY BASED AND CATEGORY BASED

        logger = Logger.getLogger(SmartHome.class.getName());
        FileHandler logFileHandler;

        try {
            logFileHandler = new FileHandler("SmartHome.log");
            logger.addHandler(logFileHandler);

            SimpleFormatter formatter = new SimpleFormatter();
            logFileHandler.setFormatter(formatter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startLogging() {
        System.out.println("Logging started");
        try {
            scheduler.scheduleAtFixedRate(this::log, 0, 2, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            System.err.println("Error during logging: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void log() {
        LogTask task = loggingList.peekAndRemove();

        while(task != null) {
            logger.log(task.getLogLevel(), task.getMessage());
            task = loggingList.peekAndRemove();
        }

    }

    private void initializeScheduler() {
        scheduler = Executors.newScheduledThreadPool(2);
        startTick();
        System.out.println("Tick started");
        initializeLogger();
        startLogging();
        System.out.println("Logging started");
    }

    private void startTick() {
        scheduler.scheduleAtFixedRate(this::tick, 0, 1, TimeUnit.SECONDS);
    }

    private void stopTick() {
        if(scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    private void tick() {
        try {
            tickTask();
        } catch (Exception e) {
            System.err.println("Error during tick execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void  addToGroupAndType(@NotNull Device device) {
        groupMap.get(device.getDeviceGroup()).addDevice(device);
        typeMap.get(device.getDeviceType()).addDevice(device);
    }

    public Device createDevice(String deviceName, String deviceType, String deviceGroup, String location) {
        return new Device(
                deviceName, deviceType, deviceGroup, location, false, 0,  0, 0, 1
        );
    };

    public Device createDevice(String deviceName, String deviceType, String deviceGroup, String location, boolean isTurnedOn, double batteryLevel, double powerConsumption, int maxBatteryCapacity, int powerLevel) {
        return new Device(
                deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel
        );
    }

    public void addDevice(@NotNull Device device) {
        if(device.isTurnedOn()) {
            poweredOnDevices.add(device);
        } else {
            poweredOffDevices.add(device);
        }
        addToGroupAndType(device);
    }

    public void addLocation(String location) {

        if(locationMap.containsKey(location)) {
            addLog(Level.WARNING, String.format("Location already exists, user tried to add %s again", location));
            return;
        }

        locationMap.put(location, new DeviceLocation(location));
    }

    private double calculateCurrentPowerConsumption() {
        return (double) poweredOnDevices.stream().map(Device::getBasePowerConsumption).reduce(0.0, Double::sum);
    }

    public void addRule(Rule rule) {
        ruleList.addEnd(rule);
    }

    public void addImmediateRule(Rule rule) {
        ruleList.addFront(rule);
    }

    public void turnOnDevice(@NotNull Device device) {
        device.setTurnedOn(true);
        poweredOnDevices.add(device);
        poweredOffDevices.remove(device);
    }

    public void turnOffDevice(@NotNull Device device) {
        device.setTurnedOn(false);
        poweredOffDevices.add(device);
        poweredOnDevices.remove(device);
    }

    private void addLog(Level logLevel, String message) {
        loggingList.addEnd(new LogTask(logLevel, message));
    }

    private void logPowerConsumption() {
        powerConsumption = calculateCurrentPowerConsumption();

        if(powerConsumption > threshold * 1.5) {
            addLog(Level.SEVERE, String.format("Power consumption is %fW, which is %.2f percent above the threshold", powerConsumption, (powerConsumption - threshold) / threshold * 100));
        }
        else if(powerConsumption > threshold) {
            addLog(Level.WARNING, String.format("Power consumption is %fW, which is %.2f percent above the threshold", powerConsumption, (powerConsumption - threshold) / threshold * 100));
        }
        else {
            addLog(Level.INFO, String.format("Power consumption - %fW, %.2f percent of threshold", powerConsumption, powerConsumption / threshold * 100));;
        }
    }

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    private void tickTask() {
        logPowerConsumption();
    }

    //another function i thought of but immediately forgot something to do with tick


    //ALSO
    //add a function to get all devices in a group or type or location
    //rule to turn back on decorative if power consumption is below threshold
}