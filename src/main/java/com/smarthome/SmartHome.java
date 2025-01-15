package com.smarthome;

import com.smarthome.datastuctures.LinkedList;
import com.smarthome.datastuctures.PriorityQueue;
import com.smarthome.devices.AirConditioner;
import com.smarthome.devices.Device;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceGroup.DeviceGroupEnum;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceLocation.DeviceLocationEnum;
import com.smarthome.enums.DeviceType;
import com.smarthome.enums.DeviceType.DeviceTypeEnum;
import com.smarthome.misc.RuleParsingException;
import com.smarthome.tasks.LogTask;
import com.smarthome.tasks.Rule;
import com.smarthome.tasks.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
@SuppressWarnings({"unused", "CallToPrintStackTrace"})

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
    private final PriorityQueue<Device> powerReducibleDevices = new PriorityQueue<>();
    private final PriorityQueue<Device> turnBackOnDevices = new PriorityQueue<>();
    // Logging lists
    private final LinkedList<LogTask> loggingList = new LinkedList<>();
    private final LinkedList<LogTask> powerConsumptionLogList = new LinkedList<>();
    private final LinkedList<LogTask> deviceBatteryLogList = new LinkedList<>();

    // Rule list
    private final LinkedList<Rule> ruleList = new LinkedList<>();

    private double powerConsumption = 1;
    private double threshold;
    private int idealTemp;
    private String mode = "Normal";
    private boolean simulate;

    // ========================================================================
    // Constructors and Initializers
    // ========================================================================

    public SmartHome(int threshold, int idealTemp, boolean simulate) {
        this.threshold = threshold;
        this.idealTemp = idealTemp;
        this.simulate = simulate;
        initialize();
    }

    private void initialize() {
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

    private void addToGroupAndType(@NotNull Device device) {
        groupMap.get(device.getDeviceGroup().name()).addDevice(device);
        typeMap.get(device.getDeviceType().name()).addDevice(device);
        locationMap.get(device.getLocation().name()).addDevice(device);
    }

    public Device createDevice(String deviceName, DeviceTypeEnum deviceType, @NotNull DeviceGroupEnum deviceGroup, DeviceLocationEnum location) {
        if (Objects.equals(deviceGroup.name().toLowerCase(), "AirConditioners".toLowerCase())) {
            return new AirConditioner(
                    deviceName, deviceType, deviceGroup, location, false, 0, 0, 0, 1, true
            );
        }
        return new Device(
                deviceName, deviceType, deviceGroup, location, false, 0, 0, 0, 1
        );
    }

    public Device createDevice(String deviceName, DeviceTypeEnum deviceType, @NotNull DeviceGroupEnum deviceGroup, DeviceLocationEnum location, boolean isTurnedOn, double batteryLevel, double powerConsumption, int maxBatteryCapacity, int powerLevel) {
        if (Objects.equals(deviceGroup.name().toLowerCase(), "AirConditioners".toLowerCase())) {
            return new AirConditioner(
                    deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel, true
            );
        }
        return new Device(
                deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, powerConsumption, maxBatteryCapacity, powerLevel
        );
    }

    public void addDevice(@NotNull Device device) {

        //AirConditioner check for REST API
        if (Objects.equals(device.getDeviceGroup().name().toLowerCase(), "AirConditioners".toLowerCase()) && device.getClass() != AirConditioner.class) {
            device = new AirConditioner(
                    device.getDeviceName(), device.getDeviceType(), device.getDeviceGroup(), device.getLocation(), device.isTurnedOn(), device.getBatteryLevel(), device.getBasePowerConsumption(), (int) device.getMaxBatteryCapacity(), device.getPowerLevel(), true
            );
        }

            if (device.isTurnedOn()) {
            poweredOnDevices.add(device);
            device.setTurnedOnTime(date.getTime());

            if (device.getDeviceType().getPriority() == Integer.MAX_VALUE) return;
            DeviceLocation location = locationMap.get(device.getLocation().name());

            deviceQueue.enqueue(new Task<>(device, device.getDeviceType().getPriority() + device.getDeviceGroup().getPriority() + (location.getPeople() * 10)));
        } else {
            poweredOffDevices.add(device);
        }
        addToGroupAndType(device);

        if (device.getPowerLevel() != 0) {
            if (device.getDeviceType().getPriority() == Integer.MAX_VALUE) return;
            DeviceLocation location = locationMap.get(device.getLocation().name());

            powerReducibleDevices.enqueue(new Task<>(device, device.getDeviceType().getPriority() + device.getDeviceGroup().getPriority() + (location.getPeople() * 10)));
        }
    }

    public void turnOnDevice(@NotNull Device device) {
        device.setTurnedOn(true);
        if(!poweredOnDevices.contains(device)) {
            poweredOnDevices.add(device);
            device.setTurnedOnTime(date.getTime());
        }
        poweredOffDevices.remove(device);
    }

    public void turnOffDevice(@NotNull Device device) {
        device.setTurnedOn(false);
        if(!poweredOffDevices.contains(device)) poweredOffDevices.add(device);
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
            if (device.getDeviceName().toLowerCase().equals(name)) {
                return device;
            }
        }
        for (Device device : poweredOffDevices) {
            if (device.getDeviceName().toLowerCase().equals(name)) {
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

    public void turnOffDevicesByGroup(String groupName) {
        groupMap.get(groupName).getDevices().forEach(this::turnOffDevice);
    }

    public void turnOnDevicesByGroup(String groupName) {
        groupMap.get(groupName).getDevices().forEach(this::turnOnDevice);
    }

    public void turnOffDevicesByType(String typeName) {
        typeMap.get(typeName).getDevices().forEach(this::turnOffDevice);

    }

    public void turnOnDevicesByType(String typeName) {
        typeMap.get(typeName).getDevices().forEach(this::turnOnDevice);
    }

    public void turnOffDevicesByLocation(String locationName) {
        locationMap.get(locationName).getDevices().forEach(this::turnOffDevice);
    }

    public void turnOnDevicesByLocation(String locationName) {
        locationMap.get(locationName).getDevices().forEach(this::turnOnDevice);
    }

    public void turnOffAllDevices() {
        poweredOnDevices.forEach(this::turnOffDevice);
    }

    public void turnOnAllDevices() {
        poweredOffDevices.forEach(this::turnOnDevice);
    }

    public Device getDevice(int id) {
        return getDeviceByID(id);
    }

    public Device getDevice(String name) {
        return getDeviceByName(name);
    }

    public void addPerson(@NotNull DeviceLocationEnum location) {
        locationMap.get(location.name()).addPeople(1);
        locationMap.get(location.name()).getDevices().forEach(device -> {
            if (device.isTurnedOn()) {
                Task<Device> task = deviceQueue.getTask(device);
                deviceQueue.updatePriority(task, task.getPriority() + 10);
            }
        });
    }

    public void removePerson(@NotNull DeviceLocationEnum location) {
        locationMap.get(location.name()).removePeople(1);
        locationMap.get(location.name()).getDevices().forEach(device -> {
            if (device.isTurnedOn()) {
                Task<Device> task = deviceQueue.getTask(device);
                deviceQueue.updatePriority(task, task.getPriority() - 10);
            }
        });
    }

    public void addPerson(@NotNull DeviceLocation location) {
        location.addPeople(1);
        location.getDevices().forEach(device -> {
            if (device.isTurnedOn()) {
                System.out.println("Old priority: " + deviceQueue.getTask(device).getPriority());
                Task<Device> task = deviceQueue.getTask(device);
                deviceQueue.updatePriority(task, task.getPriority() + 10);
                System.out.println("New priority: " + deviceQueue.getTask(device).getPriority());
            }
        });
    }

    public void removePerson(@NotNull DeviceLocation location) {
        location.addPeople(1);
        System.out.println("People in location: " + location.getDevices());
        location.getDevices().forEach(device -> {
            if (device.isTurnedOn()) {
                System.out.println("Old priority: " + deviceQueue.getTask(device).getPriority());
                Task<Device> task = deviceQueue.getTask(device);
                deviceQueue.updatePriority(task, task.getPriority() - 10);
                System.out.println("New priority: " + deviceQueue.getTask(device).getPriority());
            }
            else {
                System.out.println("Device is turned off");
            }
        });
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

    public void checkPowerConsumption() {
        double currPowerConsumption = calculateCurrentPowerConsumption();

        if (currPowerConsumption > threshold) {
            Task<Device> reducePowerTask = powerReducibleDevices.dequeue();
            if (reducePowerTask == null) {
                System.out.println("No devices to reduce power consumption");
                return;
            }
            Device device = reducePowerTask.getTask();

            if (currPowerConsumption - (device.getBasePowerConsumption() * (device.getPowerLevel() - 1)) > threshold) {
                powerReducibleDevices.enqueue(reducePowerTask);
                Task<Device> removeTask = deviceQueue.dequeue();
                if (removeTask == null) {
                    System.out.println("No devices to turn off");
                    return;
                }
                Device removeDevice = removeTask.getTask();

                System.out.println("Reducing power consumption by turning off " + device.getDeviceName());

                addRule(parseRule("turn " + removeDevice.getDeviceID() + " off"));
                turnBackOnDevices.enqueue(new Task<>(device, -removeTask.getPriority()));
            }
            else {
                addRule(parseRule("set " + device.getDeviceID() + " 1"));
            }
        }
        else {
            Task<Device> turnBackOnTask = turnBackOnDevices.dequeue();
            if (turnBackOnTask != null) {
                Device turnBackOnDevice = turnBackOnTask.getTask();
                if (currPowerConsumption + (turnBackOnDevice.getBasePowerConsumption() * turnBackOnDevice.getPowerLevel()) > threshold) {
                    System.out.println("Not turning back on " + turnBackOnTask.getTask().getDeviceName());
                    return;
                }
                System.out.println("Turning back on " + turnBackOnTask.getTask().getDeviceName());
                addRule(parseRule("turn " + turnBackOnTask.getTask().getDeviceID() + " on"));
            }
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
            checkPowerConsumption();
        }
        catch (Exception e) {
            System.err.println("Error during tickTask execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void startRuleExecution() {
        try {
            scheduler.scheduleAtFixedRate(this::executeRules, 0, 1, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            System.err.println("Error during rule execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeRules() {
        Rule rule = ruleList.peekAndRemove();
        while (rule != null) {
            executeRule(rule);
            rule = ruleList.peekAndRemove();
        }
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
         * MODE - [mode modeName]
         * */
        ArrayList<String> tokens = new ArrayList<>(List.of(ruleString.toLowerCase().split(" ")));
        ArrayList<String> validTokens = new ArrayList<>(List.of("flip", "turn", "set", "group", "type", "location", "mode"));

        if (!validTokens.contains(tokens.getFirst())) {
            throw new RuleParsingException("Invalid type of rule - " + tokens.getFirst() + "\nAvailable types - " + validTokens);
        }

        if (Objects.equals(tokens.getFirst(), "flip")) {

            tokens.removeFirst();
            Device device = checkTokenForDevice(String.join(" ", tokens));

            return new Rule(device.getDeviceID(), true, false, false, false, 0, "", false, false, "", false, false, "", false, false);

        } else if (Objects.equals(tokens.getFirst(), "turn")) {

            String stateString = tokens.getLast();
            tokens.removeLast();
            tokens.removeFirst();
            Device device = checkTokenForDevice(String.join(" ", tokens));

            boolean state;

            if (Objects.equals(stateString, "on")) {
                state = true;
            } else if (Objects.equals(stateString, "off")) {
                state = false;
            } else {
                throw new RuleParsingException("Invalid argument for turn device - " + tokens.get(2));
            }

            return new Rule(device.getDeviceID(), false, state, !state, false, 0, "", false, false, "", false, false, "", false, false);

        } else if (Objects.equals(tokens.getFirst(), "set")) {

            String stateString = tokens.getLast();
            tokens.removeLast();
            tokens.removeFirst();
            Device device = checkTokenForDevice(String.join(" ", tokens));

            if(!isNumeric(stateString)) {
                throw new RuleParsingException("Invalid argument for set power level - " + tokens.get(2));
            }

            int powerLevel = Integer.parseInt(stateString);
            if(powerLevel < 0 || powerLevel > 5) {
                throw new RuleParsingException("Invalid power level - " + powerLevel);
            }
            return new Rule(device.getDeviceID(), false, false, false, true, powerLevel, "", false, false, "", false, false, "", false, false);

        } else if (Objects.equals(tokens.getFirst(), "group")) {

            checkTokenSize(tokens, 3);
            DeviceGroup group = groupMap.get(tokens.get(1).toUpperCase());
            if(group == null) {
                throw new RuleParsingException("Group not found");
            }

            boolean state = checkTokenOnOff(tokens.get(2));

            return new Rule(-1, false, state, !state, false, 0, group.getGroupName(), state, !state, "", false, false, "", false, false);

        } else if (Objects.equals(tokens.getFirst(), "type")) {

            checkTokenSize(tokens, 3);
            DeviceType type = typeMap.get(tokens.get(1).toUpperCase());
            if(type == null) {
                throw new RuleParsingException("Type not found");
            }

            boolean state = checkTokenOnOff(tokens.get(2));

            return new Rule(-1, false, state, !state, false, 0, "", false, false, type.getTypeName(), state, !state, "", false, false);

        } else if (Objects.equals(tokens.getFirst(), "location")) {

            checkTokenSize(tokens, 3);
            DeviceLocation location = locationMap.get(tokens.get(1).toUpperCase());
            if(location == null) {
                throw new RuleParsingException("Location not found");
            }

            boolean state = checkTokenOnOff(tokens.get(2));

            return new Rule(-1, false, state, !state, false, 0, "", false, false, "", false, false, location.getLocation(), state, !state);

        } else if (Objects.equals(tokens.getFirst(), "mode")) {

            checkTokenSize(tokens, 2);
        }
        return null;
    }

    public void executeRule(@NotNull Rule rule) {

        /*"
                "deviceId=" + deviceId +
                ", flipState=" + flipState +
                ", turnOn=" + turnOn +
                ", turnOff=" + turnOff +
                ", setPowerLevel=" + setPowerLevel +
                ", powerLevel=" + powerLevel +
                ", turnGroupOff=" + turnGroupOff +
                ", turnGroupOn=" + turnGroupOn +
                ", groupName='" + groupName + '\'' +
                ", turnTypeOff=" + turnTypeOff +
                ", turnTypeOn=" + turnTypeOn +
                ", typeName='" + typeName + '\'' +
                ", turnLocationOff=" + turnLocationOff +
                ", turnLocationOn=" + turnLocationOn +
                ", locationName='" + locationName + '\'' +
                '}';*/
        Device device;
        DeviceGroup group;
        DeviceType type;
        DeviceLocation location;

        if (rule.getDeviceId() != -1) {
            device = getDevice(rule.getDeviceId());
            if (device == null) {
                throw new RuleParsingException("Device not found");
            }
            if(rule.isFlipState()) {
                device.setTurnedOn(!device.isTurnedOn());

            } else if(rule.isSetPowerLevel()) {
                if (rule.getPowerLevel() > 5 || rule.getPowerLevel() < 0) {
                    throw new RuleParsingException("Invalid power level");
                }
                device.setPowerLevel(rule.getPowerLevel());

            } else if(rule.isTurnOn()) {
                turnOnDevice(device);

            } else if(rule.isTurnOff()) {
                turnOffDevice(device);

            }
        }
        if(!rule.getGroupName().isEmpty()) {
            group = groupMap.get(rule.getGroupName().toUpperCase());
            if(group == null) {
                throw new RuleParsingException("Group not found");
            }
            if(rule.isTurnGroupOn()) {
                turnOffDevicesByGroup(group.getGroupName());

            } else if(rule.isTurnGroupOff()) {
                turnOnDevicesByGroup(group.getGroupName());
            }
        }
        if(!rule.getTypeName().isEmpty()) {
            type = typeMap.get(rule.getTypeName().toUpperCase());
            if(type == null) {
                throw new RuleParsingException("Type not found");
            }
            if(rule.isTurnTypeOn()) {
                turnOffDevicesByType(type.getTypeName());

            } else if(rule.isTurnTypeOff()) {
                turnOnDevicesByType(type.getTypeName());
            }
        }
        if(!rule.getLocationName().isEmpty()) {
            location = locationMap.get(rule.getLocationName().toUpperCase());
            if(location == null) {
                throw new RuleParsingException("Location not found");
            }
            if(rule.isTurnLocationOn()) {
                turnOffDevicesByLocation(location.getLocation());

            } else if(rule.isTurnLocationOff()) {
                turnOnDevicesByLocation(location.getLocation());
            }
        }

    }

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

            /*if (simulate) {
                double randomDouble = random.nextDouble();
                if (randomDouble >= 0.9) {
                    System.out.println("Adding person to " + location);
                    addPerson(location);
                } else if (randomDouble <= 0.1) {
                    System.out.println("Removing person from " + location);
                    if (location.getPeople() > 0) removePerson(location);
                }
            }
            */
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
                    addRule(parseRule("turn " + airConditioner.getDeviceID() + " on"));
                    addRule(parseRule("set " + airConditioner.getDeviceID() + "1"));
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
            addRule(parseRule("set " + airConditioner.getDeviceID() + " 1"));
            addRule(parseRule("turn " + airConditioner.getDeviceID() + " off"));
            return;
        }

        if (idealTemp - location.getTemperature() > 0) {
            airConditioner.setMode(false);
            addRule(parseRule("set " + airConditioner.getDeviceID() + " " + (idealTemp - location.getTemperature() > 5 ? 5 : (int) (idealTemp - location.getTemperature()))));
        } else {
            airConditioner.setMode(true);
            addRule(parseRule("set " + airConditioner.getDeviceID() + " " + (location.getTemperature() - idealTemp > 5 ? 5 : (int) (location.getTemperature() - idealTemp))));
        }
    }

    // ========================================================================
    // Device State Checks and Simulations
    // ========================================================================

    private void checkEachDevice() {
        for (Device device : poweredOnDevices) {
            if (device.getClass() == AirConditioner.class) {
                tempCheck((AirConditioner) device, locationMap.get(device.getLocation().name()));
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

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public List<Device> getDevicesByGroup(String groupName) {
        return groupMap.get(groupName).getDevices();
    }

    public ConcurrentHashMap<String, DeviceGroup> getDeviceGroups() {
        return groupMap;
    }

    public ConcurrentHashMap<String, DeviceType> getDeviceTypes() {
        return typeMap;
    }

    public ConcurrentHashMap<String, DeviceLocation> getDeviceLocations() {
        return locationMap;
    }

    public List<Device> getDevicesByType(String typeName) {
        return typeMap.get(typeName).getDevices();
    }

    public List<Device> getDevicesByLocation(String locationName) {
        return locationMap.get(locationName).getDevices();
    }

    private Device checkTokenForDevice(String token) {
        Device device;
        if(isNumeric(token)) {
            device = getDevice(Integer.parseInt(token));
        } else {
            device = getDevice(token);
        }

        if(device == null) {
            throw new RuleParsingException("Device not found");
        }
        return device;
    }

    private void checkTokenSize(@NotNull ArrayList<String> tokens, int size) {
        if(tokens.size() != size) {
            throw new RuleParsingException("Invalid number of arguments for rule");
        }
    }

    private boolean checkTokenOnOff(String token) {
        if (Objects.equals(token, "on")) {
            return true;
        } else if (Objects.equals(token, "off")) {
           return false;
        } else {
            throw new RuleParsingException("Invalid argument for turn location - " + token);
        }
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setIdealTemp(int idealTemp) {
        this.idealTemp = idealTemp;
    }

    public void setSimulate(boolean simulate) {
        this.simulate = simulate;
    }

    public double getThreshold() {
        return threshold;
    }

    public int getIdealTemp() {
        return idealTemp;
    }

    public boolean isSimulate() {
        return simulate;
    }

    public CopyOnWriteArrayList<Device> getPoweredOnDevices() {
        return poweredOnDevices;
    }

    // ========================================================================
    // Test Methods
    // ========================================================================

    @TestOnly
    public Device getDeviceByLocation(String location, String name) {
        return locationMap.get(location).getDeviceByName(name);
    }

    @TestOnly
    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    @TestOnly
    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    @TestOnly
    public void setScheduler(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }
}