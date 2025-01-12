package com.smarthome;

import com.smarthome.datastuctures.LinkedList;
import com.smarthome.datastuctures.PriorityQueue;
import com.smarthome.devices.AirConditioner;
import com.smarthome.devices.Device;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceType;
import com.smarthome.enums.DeviceGroup.DeviceGroupEnum;
import com.smarthome.enums.DeviceLocation.DeviceLocationEnum;
import com.smarthome.enums.DeviceType.DeviceTypeEnum;
import com.smarthome.misc.RuleParsingException;
import com.smarthome.tasks.LogTask;
import com.smarthome.tasks.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.*;

/**
 * Entry point for the Smart Home Automation project.
 * Automates smart home devices using data structures
 *
 * @author Abhinav Variyath
 * @author Anirudh Jayan
 * @author Tara Samiksha
 * @author Sarvesh Ram Kumar
 * @version 1.0
 * @since 2021-04-10
 * @see Device
 */
public class SmartHome {

    // ========================================================================
    // Fields
    // ========================================================================

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

    // Device queues
    private final PriorityQueue<Device> deviceQueue = new PriorityQueue<>(); // You planned to give priority based on type and group, make enum
    private final PriorityQueue<Device> powerReducableDevices = new PriorityQueue<>();

    // Logging lists
    private final LinkedList<LogTask> loggingList = new LinkedList<>();
    private final LinkedList<LogTask> powerConsumptionLogList = new LinkedList<>();
    private final LinkedList<LogTask> deviceBatteryLogList = new LinkedList<>();

    // Rule list
    private final LinkedList<Rule> ruleList = new LinkedList<>();

    private double powerConsumption = 1;
    private int threshold;
    private int idealTemp;
    private boolean simulate;

    // ========================================================================
    // Constructors and Initializers
    // ========================================================================

    SmartHome(int threshold, int idealTemp, boolean simulate) {
        this.threshold = threshold;
        this.idealTemp = idealTemp;
        this.simulate = simulate;
        initialize();
    }

    public void initialize() {
        for (DeviceGroupEnum deviceGroup : DeviceGroupEnum.values()) {
            groupMap.put(deviceGroup.name(), new DeviceGroup(deviceGroup.name()));
        }

        for (DeviceTypeEnum deviceType : DeviceTypeEnum.values()) {
            typeMap.put(deviceType.name(), new DeviceType(deviceType.name()));
        }

        for (DeviceLocationEnum location : DeviceLocationEnum.values()) {
            DeviceLocation devLocation = new DeviceLocation(location.name());
            locationMap.put(location.name(), devLocation);
            devLocation.setTemperature(random.nextInt(10, 45));
        }

        initializeScheduler();
    }


    // ========================================================================
    // Device Management
    // ========================================================================

    public void addToGroupAndType(@NotNull Device device) {
        groupMap.get(device.getDeviceGroup().name()).addDevice(device);
        typeMap.get(device.getDeviceType().name()).addDevice(device);
        locationMap.get(device.getLocation().name()).addDevice(device);
    }

    public Device createDevice(String deviceName, DeviceTypeEnum deviceType, DeviceGroupEnum deviceGroup, DeviceLocationEnum location) {
        if (Objects.equals(deviceGroup, "AirConditioners")) {
            return new AirConditioner(
                    deviceName, deviceType, deviceGroup, location, false, 0, 0, 0, 1, true
            );
        }
        return new Device(
                deviceName, deviceType, deviceGroup, location, false, 0, 0, 0, 1
        );
    }

    public Device createDevice(String deviceName, DeviceTypeEnum deviceType, DeviceGroupEnum deviceGroup, DeviceLocationEnum location, boolean isTurnedOn, double batteryLevel, double powerConsumption, int maxBatteryCapacity, int powerLevel) {
        if (Objects.equals(deviceGroup, "AirConditioners")) {
            return new AirConditioner(
                    deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel, true
            );
        }
        return new Device(
                deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel
        );
    }

    public void addDevice(@NotNull Device device) {
        if (device.isTurnedOn()) {
            poweredOnDevices.add(device);
            device.setTurnedOnTime(date.getTime());
        } else {
            poweredOffDevices.add(device);
        }
        addToGroupAndType(device);
        if (device.getPowerLevel() == 0) {
            // powerReducableDevices.enqueue();
        }
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

    public void removeDevice(@NotNull Device device) {
        poweredOnDevices.remove(device);
        poweredOffDevices.remove(device);
        groupMap.get(device.getDeviceGroup().name()).removeDevice(device);
        typeMap.get(device.getDeviceType().name()).removeDevice(device);
        locationMap.get(device.getLocation().name()).removeDevice(device);
    }

    public Device getDeviceByName(String name) {
        for (Device device : poweredOnDevices) {
            if (device.getDeviceName().equals(name)) {
                return device;
            }
        }
        for (Device device : poweredOffDevices) {
            if (device.getDeviceName().equals(name)) {
                return device;
            }
        }
        return null;
    }

    public Device getDeviceByID(int id) {
        for (Device device : poweredOnDevices) {
            if (device.getDeviceID() == id) {
                return device;
            }
        }
        for (Device device : poweredOffDevices) {
            if (device.getDeviceID() == id) {
                return device;
            }
        }
        return null;
    }

    public Device getDevice(int id) {
        return getDeviceByID(id);
    }

    public Device getDevice(String name) {
        return getDeviceByName(name);
    }

    // ========================================================================
    // Tick and Scheduling
    // ========================================================================

    private void initializeScheduler() {
        scheduler = Executors.newScheduledThreadPool(3);
        startTick();
        System.out.println("Tick started");
        initializeLogger();
        startLogging();
        System.out.println("Logging started");
        startRuleExecution();
    }

    private void startTick() {
        scheduler.scheduleAtFixedRate(this::tick, 0, 1, TimeUnit.SECONDS);
    }

    private void stopTick() {
        if (scheduler != null && !scheduler.isShutdown()) {
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

    private void tickTask() {
        tickCount++;
        if (tickCount % 2 == 0) {
            try {
                if (simulate) simulateDeviceChange();
                realisticPowerConsumption();
            } catch (Exception e) {
                System.err.println("Error during deviceChange execution: " + e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            logPowerConsumption();
            reduceBatteryTick();
            checkEachDevice();
            checkEachLocation();
        }
        catch (Exception e) {
            System.err.println("Error during tickTask execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void startRuleExecution() {
        scheduler.scheduleAtFixedRate(this::executeRules, 0, 1, TimeUnit.SECONDS);
    }

    private void executeRules() {
        Rule rule = ruleList.peekAndRemove();
        // Implement rule execution logic here
    }

    // ========================================================================
    // Logging
    // ========================================================================

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
        } catch (Exception e) {
            System.err.println("Error during logging: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void log() {
        LogTask task = loggingList.peekAndRemove();
        LogTask powerTask = powerConsumptionLogList.peekAndRemove();
        LogTask batteryTask = deviceBatteryLogList.peekAndRemove();

        while (task != null) {
            logger.log(task.getLogLevel(), task.getMessage());
            task = loggingList.peekAndRemove();
        }

        while (powerTask != null) {
            powerConsumptionlogger.log(powerTask.getLogLevel(), powerTask.getMessage());
            powerTask = powerConsumptionLogList.peekAndRemove();
        }

        while (batteryTask != null) {
            deviceBatteryLogger.log(batteryTask.getLogLevel(), batteryTask.getMessage());
            batteryTask = deviceBatteryLogList.peekAndRemove();
        }
    }

    private void addLog(Level logLevel, String message) {
        loggingList.addEnd(new LogTask(logLevel, message));
    }

    private void addPowerLog(Level logLevel, String message) {
        powerConsumptionLogList.addEnd(new LogTask(logLevel, message));
        if (!logLevel.equals(Level.INFO)) {
            addLog(logLevel, message);
        }
    }

    private void addBatteryLog(Level logLevel, String message) {
        deviceBatteryLogList.addEnd(new LogTask(logLevel, message));
        if (!logLevel.equals(Level.INFO)) {
            addLog(logLevel, message);
        }
    }

    // ========================================================================
    // Power Management
    // ========================================================================

    private double calculateCurrentBasePowerConsumption() {
        return poweredOnDevices.stream().map(Device::getBasePowerConsumption).reduce(0.0, Double::sum);
    }

    private double calculateCurrentPowerConsumption() {
        return poweredOnDevices.stream()
                .mapToDouble(device -> device.getBasePowerConsumption() * (device.getPowerLevel() > 0 ? device.getPowerLevel() : 1))
                .sum();
    }

    private void logPowerConsumption() {
        powerConsumption = calculateCurrentPowerConsumption();

        if (powerConsumption > threshold * 1.5) {
            addPowerLog(Level.SEVERE, String.format("Power consumption is %fW, which is %.2f percent above the threshold", powerConsumption, (powerConsumption - threshold) / threshold * 100));
        } else if (powerConsumption > threshold) {
            addPowerLog(Level.WARNING, String.format("Power consumption is %fW, which is %.2f percent above the threshold", powerConsumption, (powerConsumption - threshold) / threshold * 100));
        } else {
            addPowerLog(Level.INFO, String.format("Power consumption - %fW, %.2f percent of threshold", powerConsumption, powerConsumption / threshold * 100));
            ;
        }
    }

    public Double getPowerConsumption() {
        return powerConsumption;
    }

    private void realisticPowerConsumption() {
        for (Device device : poweredOnDevices) {
            if (random.nextDouble() >= 0.5) {
                device.setBasePowerConsumption(device.getBasePowerConsumption() + device.getBasePowerConsumption() * random.nextDouble());
            } else {
                device.setBasePowerConsumption(device.getBasePowerConsumption() - device.getBasePowerConsumption() * random.nextDouble());
            }
        }
    }

    // ========================================================================
    // Battery Management
    // ========================================================================

    private void reduceBatteryTick() {
        for (Device device : poweredOnDevices) {
            if (device.isOnBattery()) {
                boolean toLog = reduceBatteryLevel(device);
                if (device.getBatteryLevel() < 20) {
                    addBatteryLog(Level.WARNING, String.format("Battery level of %s is below 20 percent!", device.getDeviceName()));
                } else if (device.getBatteryLevel() < 10) {
                    addBatteryLog(Level.SEVERE, String.format("Battery level of %s is below 10 percent!", device.getDeviceName()));
                } else if (!toLog) {
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

    // ========================================================================
    // Rule Management
    // ========================================================================

    public void addRule(Rule rule) {
        ruleList.addEnd(rule);
    }

    public void addImmediateRule(Rule rule) {
        ruleList.addFront(rule);
    }

    // ========================================================================
    // Location and Temperature Management
    // ========================================================================

    public void addLocation(String location) {
        if (locationMap.containsKey(location)) {
            addLog(Level.WARNING, String.format("Location already exists, user tried to add %s again", location));
            return;
        }
        locationMap.put(location, new DeviceLocation(location));
    }

    private void checkEachLocation() {
        for (DeviceLocation location : locationMap.values()) {
            if (location.getTemperature() > 40) {
                addLog(Level.SEVERE, String.format("Temperature in %s is above 40 degrees!", location));
            } else if (location.getTemperature() > 35) {
                addLog(Level.WARNING, String.format("Temperature in %s is above 35 degrees!", location));
            } else if (location.getTemperature() < 10) {
                addLog(Level.SEVERE, String.format("Temperature in %s is below 10 degrees!", location));
            } else if (location.getTemperature() < 15) {
                addLog(Level.WARNING, String.format("Temperature in %s is below 15 degrees!", location));
            }

            if (location.getTemperature() != idealTemp) {
                Device airConditioner = location.getDeviceByName("AirConditioner");
                if (airConditioner != null) {
                    turnOnDevice(airConditioner);
                }
            }
        }
    }

    private void tempCheck(@NotNull AirConditioner airConditioner, @NotNull DeviceLocation location) {
        if (airConditioner.getMinutesSinceTempChange() >= 1) {
            airConditioner.setSimulationTempChangeTime(date.getTime());

            if (airConditioner.getMode()) {
                location.setTemperature(location.getTemperature() - airConditioner.getPowerLevel());
            } else {
                location.setTemperature(location.getTemperature() + airConditioner.getPowerLevel());
            }
        }

        if (idealTemp == location.getTemperature()) {
            addLog(Level.INFO, "Temperature is ideal, turning off AirConditioner in " + location);
            airConditioner.setPowerLevel(1);
            turnOffDevice(airConditioner);
            return;
        }

        if (idealTemp - location.getTemperature() > 0) {
            airConditioner.setMode(false);
            airConditioner.setPowerLevel(idealTemp - location.getTemperature() > 5 ? 5 : (int) (idealTemp - location.getTemperature()));
        } else {
            airConditioner.setMode(true);
            airConditioner.setPowerLevel(location.getTemperature() - idealTemp > 5 ? 5 : (int) (location.getTemperature() - idealTemp));
        }
    }

    // ========================================================================
    // Device State Checks and Simulations
    // ========================================================================

    private void checkEachDevice() {
        for (Device device : poweredOnDevices) {
            if (device.getClass() == AirConditioner.class) {
                tempCheck((AirConditioner) device, locationMap.get(device.getLocation()));
            }
        }
    }

    private void simulateDeviceChange() {
        List<Device> toTurnOff = new ArrayList<>();
        List<Device> toTurnOn = new ArrayList<>();

        for (Device device : poweredOnDevices) {
            if (device.getClass() != Device.class) {
                continue;
            }
            double randomDouble = random.nextDouble();
            if (randomDouble >= 0.6) {
                toTurnOff.add(device);
                device.flipInteractionState();
            } else if (device.getPowerLevel() != 0) {
                device.setPowerLevel(random.nextInt(1, 6));
            }
        }

        for (Device device : poweredOffDevices) {
            if (device.getClass() != Device.class) {
                continue;
            }
            if (device.getInteraction()) {
                device.flipInteractionState();
            } else if (random.nextDouble() >= 0.6) {
                toTurnOn.add(device);
            }
        }

        toTurnOff.forEach(this::turnOffDevice);
        toTurnOn.forEach(this::turnOnDevice);
    }



    private void accidentallyturnedoncheck() {
        // Implementation for checking accidentally turned on devices
    }

    private void roommemberscheck() {
        // Implementation for checking room members presence
    }

    private void changeMode() {
        // Implementation for changing device modes
    }

    // ========================================================================
    // Utility and Helper Methods
    // ========================================================================

    public List<Device> getDevicesByGroup(String groupName) {
        return groupMap.get(groupName).getDevices();
    }

    public List<Device> getDevicesByType(String typeName) {
        return typeMap.get(typeName).getDevices();
    }

    public List<Device> getDevicesByLocation(String locationName) {
        return locationMap.get(locationName).getDevices();
    }

    public Rule parseRule(@NotNull String ruleString) {

        //maybe add if conditions later
        //like samsung routines idk

        /*
        * FLIP - [flip deviceId/deviceName]
        * TURN - [turn deviceId/deviceName on/off]
        * SET - [set deviceId/deviceName powerLevel]
        * GROUP - [group groupName on/off]
        * TYPE - [type typeName on/off]
        * LOCATION - [location locationName on/off]
        * MODE - [mode deviceId/deviceName mode]
        * */
        ArrayList<String> tokens = new ArrayList<>(List.of(ruleString.toLowerCase().split(" ")));
        ArrayList<String> validTokens = new ArrayList<>(List.of("flip", "turn", "set", "group", "type", "location", "mode"));

        if (!validTokens.contains(tokens.getFirst())) {
            throw new RuleParsingException("Invalid type of rule - " + tokens.getFirst() + "\nAvailable types - " + validTokens);
        }

        if (Objects.equals(tokens.getFirst(), "flip")) {
            if(tokens.size() != 2) {
                throw new RuleParsingException("Invalid number of arguments for flip rule");
            }

        }

        return null;
    }

    // ========================================================================
    // Test Methods
    // ========================================================================

    @TestOnly
    public Device getDeviceByLocation(String location, String name) {
        return locationMap.get(location).getDeviceByName(name);
    }
}