package com.smarthome;

import com.smarthome.datastuctures.PriorityQueue;
import com.smarthome.devices.Device;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceType;
import com.smarthome.tasks.LogTask;
import com.smarthome.tasks.Rule;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.smarthome.DeviceService;

@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = "*")
public class SmartHomeController {

    public SmartHome smartHome = new SmartHome(10,25,false);
    private final DeviceService deviceService;

    public SmartHomeController(com.smarthome.DeviceService deviceService) {
        this.deviceService = deviceService;
        smartHome.addDevice(smartHome.createDevice("Living Room Light", DeviceType.DeviceTypeEnum.DECORATIVE, DeviceGroup.DeviceGroupEnum.LIGHTS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.15, 5000, 1));
        smartHome.addDevice(smartHome.createDevice("Bedroom Light", DeviceType.DeviceTypeEnum.ENTERTAINMENT, DeviceGroup.DeviceGroupEnum.LIGHTS, DeviceLocation.DeviceLocationEnum.BEDROOM, true, 100, 0.10, 4000, 1));
        smartHome.addDevice(smartHome.createDevice("Chandelier", DeviceType.DeviceTypeEnum.DECORATIVE, DeviceGroup.DeviceGroupEnum.LIGHTS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.50, 4500, 1));
        smartHome.addDevice(smartHome.createDevice("Garden Light", DeviceType.DeviceTypeEnum.DECORATIVE, DeviceGroup.DeviceGroupEnum.LIGHTS, DeviceLocation.DeviceLocationEnum.GARDEN, true, 100, 0.20, 6000, 1));
        smartHome.addDevice(smartHome.createDevice("Ceiling Fan (Living Room)", DeviceType.DeviceTypeEnum.HEALTH, DeviceGroup.DeviceGroupEnum.FANS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.30, 10000, 1));
        smartHome.addDevice(smartHome.createDevice("Ceiling Fan (Bedroom)", DeviceType.DeviceTypeEnum.OFFICE, DeviceGroup.DeviceGroupEnum.FANS, DeviceLocation.DeviceLocationEnum.BEDROOM, true, 100, 0.25, 10000, 1));
        smartHome.addDevice(smartHome.createDevice("Desk Fan", DeviceType.DeviceTypeEnum.OTHERS, DeviceGroup.DeviceGroupEnum.FANS, DeviceLocation.DeviceLocationEnum.OFFICE, true, 100, 0.20, 5000, 1));
        smartHome.addDevice(smartHome.createDevice("Smart Alarm Clock", DeviceType.DeviceTypeEnum.HEALTH, DeviceGroup.DeviceGroupEnum.ALARMS, DeviceLocation.DeviceLocationEnum.BEDROOM, true, 100, 0.05, 3000, 0));
        smartHome.addDevice(smartHome.createDevice("Fire Alarm", DeviceType.DeviceTypeEnum.SECURITY, DeviceGroup.DeviceGroupEnum.ALARMS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.03, 3000, 0));
        smartHome.addDevice(smartHome.createDevice("Doorbell Camera", DeviceType.DeviceTypeEnum.SECURITY, DeviceGroup.DeviceGroupEnum.CAMERAS, DeviceLocation.DeviceLocationEnum.ENTRANCE, true, 100, 0.04, 3000, 0));
        smartHome.addDevice(smartHome.createDevice("Living Room Camera", DeviceType.DeviceTypeEnum.SECURITY, DeviceGroup.DeviceGroupEnum.CAMERAS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.05, 100, 0));
        smartHome.addDevice(smartHome.createDevice("Chandelier", DeviceType.DeviceTypeEnum.DECORATIVE, DeviceGroup.DeviceGroupEnum.LIGHTS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.50, 4500, 1));
        smartHome.addDevice(smartHome.createDevice("Living Room AC", DeviceType.DeviceTypeEnum.NECESSARY, DeviceGroup.DeviceGroupEnum.AIRCONDITIONERS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 0, 0.20, 0, 1));

        System.out.println("Access the API at C:\\Users\\lenovo\\Documents\\GitHub\\Smart-Home-Automation-Java\\src\\frontend\\index.html");
    }


    // ========================================================================
    //GET REQUESTS
    // ========================================================================

    @GetMapping
    public Collection<Device> getOnDevices() {
        return smartHome.getDevices();
    }

    @GetMapping("/off")
    public Collection<Device> getOffDevices() {
        return smartHome.getPoweredOffDevices();
    }

    @GetMapping("/on")
    public Collection<Device> getAllDevices() {
        return smartHome.getPoweredOnDevices();
    }

    @GetMapping("/id/{id}")
    public Device getDeviceByID(@PathVariable("id") String id) {
        return smartHome.getDeviceByID(Integer.parseInt(id));
    }

    @GetMapping("/name/{name}")
    public Device getDeviceByName(@PathVariable("name") String name){
        return smartHome.getDeviceByName(name);
    }

    @GetMapping("/power_consumption")
    public double getPowerConsumption() {
        return smartHome.getPowerConsumption();
    }

    @GetMapping("/threshold")
    public double getPowerConsumptionThreshold() {
        return smartHome.getThreshold();
    }

    @GetMapping("/ideal_temp")
    public int getIdealTemperature() {
        return smartHome.getIdealTemp();
    }

    @GetMapping("/groups")
    public Collection<String> getDeviceGroups() {
        return smartHome.getDeviceGroups().keySet();
    }

    @GetMapping("/locations")
    public Collection<String> getDeviceLocations() {
        return smartHome.getDeviceLocations().keySet();
    }

    @GetMapping("/types")
    public Collection<String> getDeviceTypes() {
        return smartHome.getDeviceTypes().keySet();
    }

    @GetMapping("/groups/devices")
    public List<Device> getDevicesByGroup(@RequestParam String group) {
        return smartHome.getDevicesByGroup(group);
    }

    @GetMapping("/locations/devices")
    public List<Device> getDevicesByLocation(@RequestParam String location) {
        return smartHome.getDevicesByLocation(location);
    }

    @GetMapping("/types/devices")
    public List<Device> getDevicesByType(@RequestParam String type) {
        return smartHome.getDevicesByType(type);
    }

    @GetMapping("/name/{name}/power_level")
    public int getPowerLevelByName(@PathVariable("name") String name) {
        return smartHome.getDeviceByName(name).getPowerLevel();
    }

    @GetMapping("/id/{id}/power_level")
    public int getPowerLevelByID(@PathVariable("id") String id) {
        return smartHome.getDeviceByID(Integer.parseInt(id)).getPowerLevel();
    }

    @GetMapping("/name/{name}/battery_level")
    public double getBatteryLevelByName(@PathVariable("name") String name) {
        return smartHome.getDeviceByName(name).getBatteryLevel();
    }

    @GetMapping("/id/{id}/battery_level")
    public double getBatteryLevelByID(@PathVariable("id") String id) {
        return smartHome.getDeviceByID(Integer.parseInt(id)).getBatteryLevel();
    }

    @GetMapping("/name/{name}/max_battery_capacity")
    public double getMaxBatteryCapacityByName(@PathVariable("name") String name) {
        return smartHome.getDeviceByName(name).getMaxBatteryCapacity();
    }

    @GetMapping("/id/{id}/max_battery_capacity")
    public double getMaxBatteryCapacityByID(@PathVariable("id") String id) {
        return smartHome.getDeviceByID(Integer.parseInt(id)).getMaxBatteryCapacity();
    }

    @GetMapping("/name/{name}/power_consumption")
    public double getPowerConsumptionByName(@PathVariable("name") String name) {
        return smartHome.getDeviceByName(name).getBasePowerConsumption();
    }

    @GetMapping("/id/{id}/power_consumption")
    public double getPowerConsumptionByID(@PathVariable("id") String id) {
        return smartHome.getDeviceByID(Integer.parseInt(id)).getBasePowerConsumption();
    }

    @GetMapping("/log/info")
    public Collection<String> getInfoLogs() {
        ArrayList<String> logs = (ArrayList<String>) smartHome.getInfoTasks().clone();
        smartHome.clearInfoTasks();
        return logs;
    }

    @GetMapping("/log/warning")
    public Collection<String> getWarningLogs() {
        ArrayList<String> logs = (ArrayList<String>) smartHome.getWarningTasks().clone();
        smartHome.clearWarningTasks();
        return logs;
    }

    @GetMapping("/log/severe")
    public Collection<String> getErrorLogs() {
        ArrayList<String> logs = (ArrayList<String>) smartHome.getSevereTasks().clone();
        smartHome.clearSevereTasks();
        return logs;
    }

    @GetMapping("/log/power_consumption")
    public Collection<String> getPowerConsumptionLogs() {
        ArrayList<String> logs = (ArrayList<String>) smartHome.getPowerConsumptionTasks().clone();
        smartHome.clearPowerConsumptionTasks();
        return logs;
    }

    @GetMapping("/log/battery")
    public Collection<String> getBatteryLogs() {
        ArrayList<String> logs = (ArrayList<String>) smartHome.getDeviceBatteryTasks().clone();
        smartHome.clearDeviceBatteryTasks();
        return logs;
    }

    @GetMapping("/debug/linkedlists")
    public Map<String, List<Object>> getLinkedLists() {
        Map<String, List<Object>> linkedListsData = new HashMap<>();
        linkedListsData.put("loggingList", convertLogTaskList(smartHome.getLoggingList()));
        linkedListsData.put("powerConsumptionLogList", convertLogTaskList(smartHome.getPowerConsumptionLogList()));
        linkedListsData.put("deviceBatteryLogList", convertLogTaskList(smartHome.getDeviceBatteryLogList()));
        linkedListsData.put("ruleList", convertRuleList(smartHome.getRuleList()));
        return linkedListsData;
    }

    @GetMapping("/debug/priorityqueues")
    public Map<String, List<Object>> getPriorityQueues() {
        Map<String, List<Object>> priorityQueuesData = new HashMap<>();
        priorityQueuesData.put("deviceQueue", convertDeviceQueue(smartHome.getDeviceQueue()));
        priorityQueuesData.put("powerReducibleDevices", convertDeviceQueue(smartHome.getPowerReducibleDevices()));
        priorityQueuesData.put("turnBackOnDevices", convertDeviceQueue(smartHome.getTurnBackOnDevices()));
        return priorityQueuesData;
    }

    private List<Object> convertLogTaskList(com.smarthome.datastuctures.LinkedList<LogTask> list) {
        List<Object> convertedList = new ArrayList<>();

        for (int i = 0; i < list.getSize(); i++) {
            Map<String, Object> taskMap = new HashMap<>();
            taskMap.put("level", list.get(i).getLogLevel().toString());
            taskMap.put("message", list.get(i).getMessage());
            convertedList.add(taskMap);
        }

        return convertedList;
    }

    private List<Object> convertRuleList(com.smarthome.datastuctures.LinkedList<Rule> list) {
        List<Object> convertedList = new ArrayList<>();
        for(int i = 0; i < list.getSize(); i++) {
            convertedList.add(list.get(i).toString());
        }
        return convertedList;
    }

    private  List<Object> convertDeviceQueue(PriorityQueue<Device> queue) {
        List<Object> convertedList = new ArrayList<>();

        /*queue.forEach(deviceTask -> {
            Map<String, Object> map = new HashMap<>();
            map.put("Name",deviceTask.getTask().getDeviceName());
            map.put("Priority", deviceTask.getPriority());
            convertedList.add(map);
        });*/

        for(int i = 0; i < queue.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("Name", queue.get(i).getTask());
            map.put("Priority", queue.get(i).getPriority());
            convertedList.add(map);
        }

        return convertedList;
    }

    // ========================================================================
    //POST REQUESTS
    // ========================================================================

    @PostMapping
    public String addDevice(@RequestBody Device device) {
        smartHome.addDevice(device);


        //deviceService.addDevice(device);
        //just adds directly to devices
        //plan to conver to user collection

        return "Device added successfully";
    }


    // ========================================================================
    //PUT REQUESTS
    // ========================================================================

    @PutMapping("/id/{id}/on")
    public String turnOnDeviceByID(@PathVariable("id") String id) {
        smartHome.turnOnDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device turned on successfully";
    }

    @PutMapping("/name/{name}/on")
    public String turnOnDeviceByName(@PathVariable("name") String name) {
        smartHome.turnOnDevice(smartHome.getDeviceByName(name));
        return "Device turned on successfully";
    }

    @PutMapping("/id/{id}/off")
    public String turnOffDeviceByID(@PathVariable("id") String id) {
        smartHome.turnOffDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device turned on successfully";
    }

    @PutMapping("/name/{name}/off")
    public String turnOffDeviceByName(@PathVariable("name") String name) {
        smartHome.turnOffDevice(smartHome.getDeviceByName(name));
        return "Device turned on successfully";
    }

    @PutMapping("/threshold/{threshold}")
    public String setPowerConsumptionThreshold(@PathVariable("threshold") String threshold) {
        smartHome.setThreshold(Double.parseDouble(threshold));
        return "Threshold set successfully";
    }

    @PutMapping("/ideal_temp/{idealTemp}")
    public String setIdealTemperature(@PathVariable("idealTemp") String idealTemp) {
        smartHome.setIdealTemp(Integer.parseInt(idealTemp));
        return "Ideal temperature set successfully";
    }

    @PutMapping("/id/{id}/power_level/{power_level}")
    public String setPowerLevelByID(@PathVariable("id") String id, @PathVariable("power_level") String power_level) {
        smartHome.addRule(smartHome.parseRule(String.format("set %s %s", id, power_level)));
        return "Power level set successfully";
    }

    @PutMapping("/name/{name}/power_level/{power_level}")
    public String setPowerLevelByName(@PathVariable("name") String name, @PathVariable("power_level") String power_level) {
        smartHome.addRule(smartHome.parseRule(String.format("set %s %s", name, power_level)));
        return "Power level set successfully";
    }

    @PutMapping("/location/{location}/add_person")
    public String addPersonToLocation(@PathVariable("location") String location) {
        smartHome.addPerson(smartHome.getLocation(location));
        return "Person added successfully";
    }

    @PutMapping("/location/{location}/remove_person")
    public String removePersonFromLocation(@PathVariable("location") String location) {
        smartHome.removePerson(smartHome.getLocation(location));
        return "Person removed successfully";
    }


    // ========================================================================
    //DELETE REQUESTS
    // ========================================================================


    @DeleteMapping("/name/{name}")
    public String removeDeviceByName(@PathVariable("name") String name) {
        smartHome.removeDevice(smartHome.getDeviceByName(name));
        return "Device removed successfully";
    }

    @DeleteMapping("/id/{id}")
    public String removeDeviceByID(@PathVariable("id") String id) {
        smartHome.removeDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device removed successfully";
    }

}

