package com.smarthome;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Random;
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
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.*;

public class SmartHome {

    int tickCount = 0;
    
    private final ConcurrentHashMap<String, DeviceGroup> groupMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, DeviceType> typeMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, DeviceLocation> locationMap = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<Device> poweredOnDevices = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Device> poweredOffDevices = new CopyOnWriteArrayList<>();
    private ScheduledExecutorService scheduler;

    private final Random random = new Random();

    private final Date date = new Date();

    private Logger powerConsumptionlogger;
    private Logger logger;
    private Logger deviceBatteryLogger;
    private final ReentrantLock lock = new ReentrantLock();

    //device queue, you planned to give priority based on type and group, make enum
    private final PriorityQueue<Device> deviceQueue = new PriorityQueue<>();

    //another queue but then you can also make it so that you can reduce power level and if that makes it go below the threshold, do that before turning it off
    private final PriorityQueue<Device> powerReducableDevices = new PriorityQueue<>();

    private final LinkedList<LogTask> loggingList = new LinkedList<>();
    private final LinkedList<LogTask> powerConsumptionLogList = new LinkedList<>();
    private final LinkedList<LogTask> deviceBatteryLogList = new LinkedList<>();

    // add rules here and complete the whole list in a tick, add another thread for that maybe? is that really needed? who tf knows? might as well go on with the multithreading 😱
    private final LinkedList<Rule> ruleList = new LinkedList<>();

    private double powerConsumption = 1;

    private final int threshold = 10;

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

        for (String deviceGroup : deviceGroupList) {
            groupMap.put(deviceGroup, new DeviceGroup(deviceGroup));
        }
        for (String deviceType : deviceTypeList) {
            typeMap.put(deviceType, new DeviceType(deviceType));
        }
        for (String location:  locationList) {
            DeviceLocation devLocation = new DeviceLocation(location);
            locationMap.put(location, devLocation);
            devLocation.setTemperature(random.nextInt(10, 45));
        }

        initializeScheduler();
    }

    private void initializeLogger() {

        powerConsumptionlogger = Logger.getLogger("PowerConsumptionLog");
        logger = Logger.getLogger(SmartHome.class.getName());
        deviceBatteryLogger = Logger.getLogger("DeviceBatteryLog");

        FileHandler infoFileHandler;
        FileHandler warningFileHandler;
        FileHandler severeFileHandler;
        FileHandler powerConsumptionFileHandler;
        FileHandler deviceBatteryFileHandler;

        try {

            powerConsumptionlogger.setLevel(Level.ALL);
            powerConsumptionFileHandler = new FileHandler("PowerConsumption.log");
            powerConsumptionFileHandler.setLevel(Level.ALL);
            powerConsumptionFileHandler.setFormatter(new SimpleFormatter());

            infoFileHandler = new FileHandler("Info.log");
            infoFileHandler.setLevel(Level.INFO);
            infoFileHandler.setFormatter(new SimpleFormatter());
            infoFileHandler.setFilter(record -> record.getLevel() == Level.INFO);

            warningFileHandler = new FileHandler("Warning.log");
            warningFileHandler.setLevel(Level.WARNING);
            warningFileHandler.setFormatter(new SimpleFormatter());
            warningFileHandler.setFilter(record -> record.getLevel() == Level.WARNING);

            severeFileHandler = new FileHandler("Severe.log");
            severeFileHandler.setLevel(Level.SEVERE);
            severeFileHandler.setFormatter(new SimpleFormatter());
            severeFileHandler.setFilter(record -> record.getLevel() == Level.SEVERE);

            deviceBatteryLogger.setLevel(Level.ALL);
            deviceBatteryFileHandler = new FileHandler("DeviceBattery.log");
            deviceBatteryFileHandler.setLevel(Level.ALL);
            deviceBatteryFileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(infoFileHandler);
            logger.addHandler(warningFileHandler);
            logger.addHandler(severeFileHandler);
            powerConsumptionlogger.addHandler(powerConsumptionFileHandler);
            powerConsumptionlogger.setUseParentHandlers(false);
            logger.setUseParentHandlers(false);
            deviceBatteryLogger.addHandler(deviceBatteryFileHandler);
            deviceBatteryLogger.setUseParentHandlers(false);

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
        LogTask powerTask = powerConsumptionLogList.peekAndRemove();
        LogTask batteryTask = deviceBatteryLogList.peekAndRemove();

        while(task != null) {
            logger.log(task.getLogLevel(), task.getMessage());
            task = loggingList.peekAndRemove();
        }

        while(powerTask != null) {
            powerConsumptionlogger.log(powerTask.getLogLevel(), powerTask.getMessage());
            powerTask = powerConsumptionLogList.peekAndRemove();
        }

        while(batteryTask != null) {
            deviceBatteryLogger.log(batteryTask.getLogLevel(), batteryTask.getMessage());
            batteryTask = deviceBatteryLogList.peekAndRemove();
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
        tickCount++;
        if(tickCount % 2 == 0) {
            try {
                 simulateDeviceChange();
            }
            catch (Exception e) {
                System.err.println("Error during deviceChange execution: " + e.getMessage());
                e.printStackTrace();
            }
        }
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
        locationMap.get(device.getLocation()).addDevice(device);
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
            device.setTurnedOnTime(date.getTime());
        } else {
            poweredOffDevices.add(device);
        }
        addToGroupAndType(device);
        if (device.getPowerLevel() == 0) {
            //powerReducableDevices.enqueue();
        }
    }

    private void reduceBatteryTick() {
        for (Device device : poweredOnDevices) {
            if(device.isOnBattery()) {
                 boolean toLog = reduceBatteryLevel(device);
                if(device.getBatteryLevel() < 20) {
                    addBatteryLog(Level.WARNING, String.format("Battery level of %s is below 10 percent!", device.getDeviceName()));
                }
                else if(device.getBatteryLevel() < 10) {
                    addBatteryLog(Level.SEVERE, String.format("Battery level of %s is below 5 percent!", device.getDeviceName()));
                }
                else if(!toLog){
                    addBatteryLog(Level.INFO, String.format("Battery level of %s is now %s", device.getDeviceName(), device.getBatteryLevel()));
                }
            }
        }
    }

    private boolean reduceBatteryLevel(Device device) {
        int currentBattery = (int) device.getBatteryLevel();
        device.setCurrentBatteryCapacity(device.getCurrentBatteryCapacity() - (device.getBasePowerConsumption() * device.getPowerLevel()));
        device.setBatteryLevel((int) (device.getCurrentBatteryCapacity() / device.getMaxBatteryCapacity() * 100));
        return ((int) device.getBatteryLevel()) == currentBattery;
    }

    public void addLocation(String location) {

        if(locationMap.containsKey(location)) {
            addLog(Level.WARNING, String.format("Location already exists, user tried to add %s again", location));
            return;
        }

        locationMap.put(location, new DeviceLocation(location));
    }

    private double calculateCurrentBasePowerConsumption() {
        return (double) poweredOnDevices.stream().map(Device::getBasePowerConsumption).reduce(0.0, Double::sum);
    }

    private double calculateCurrentPowerConsumption() {
        return poweredOnDevices.stream()
                .mapToDouble(device -> device.getBasePowerConsumption() * (device.getPowerLevel() > 0 ? device.getPowerLevel() : 1))
                .sum();
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

    private void addPowerLog(Level logLevel, String message) {
        powerConsumptionLogList.addEnd(new LogTask(logLevel, message));

        if(!logLevel.equals(Level.INFO)) {
            addLog(logLevel, message);
        }

    }

    private void addBatteryLog(Level logLevel, String message) {
        deviceBatteryLogList.addEnd(new LogTask(logLevel, message));

        if(!logLevel.equals(Level.INFO)) {
            addLog(logLevel, message);
        }
    }

    private void logPowerConsumption() {
        powerConsumption = calculateCurrentPowerConsumption();

        if(powerConsumption > threshold * 1.5) {
            addPowerLog(Level.SEVERE, String.format("Power consumption is %fW, which is %.2f percent above the threshold", powerConsumption, (powerConsumption - threshold) / threshold * 100));
        }
        else if(powerConsumption > threshold) {
            addPowerLog(Level.WARNING, String.format("Power consumption is %fW, which is %.2f percent above the threshold", powerConsumption, (powerConsumption - threshold) / threshold * 100));
        }
        else {
            addPowerLog(Level.INFO, String.format("Power consumption - %fW, %.2f percent of threshold", powerConsumption, powerConsumption / threshold * 100));;
        }
    }

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    private void tickTask() {
        logPowerConsumption();
        reduceBatteryTick();
    }

    private void simulateDeviceChange() {
        List<Device> toTurnOff = new ArrayList<>();
        List<Device> toTurnOn = new ArrayList<>();

        for (Device device : poweredOnDevices) {
            double randomDouble = random.nextDouble();
            if (randomDouble >= 0.6) {
                toTurnOff.add(device);
                device.flipInteractionState();
            } else if (device.getPowerLevel() != 0) {
                device.setPowerLevel(random.nextInt(1, 6));
            }
        }

        for (Device device : poweredOffDevices) {
            if (device.getInteraction()) {
                device.flipInteractionState();
            } else if (random.nextDouble() >= 0.6) {
                toTurnOn.add(device);
            }
        }

        toTurnOff.forEach(this::turnOffDevice);
        toTurnOn.forEach(this::turnOnDevice);
    }

    private void tempCheck(DeviceLocation location) {



    }

    private void accidentallyturnedoncheck() {

    }
    private void roommemberscheck() {

    }
    private void changeMode() {

    }

    //another function i thought of but immediately forgot something to do with tick

    //ALSO
    //add a function to get all devices in a group or type or location
    //rule to turn back on decorative if power consumption is below threshold
}