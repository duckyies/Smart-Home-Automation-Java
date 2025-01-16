package com.smarthome;

import com.smarthome.devices.Device;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.List;
import com.smarthome.DeviceService;

@RestController
@RequestMapping("/devices")
@CrossOrigin(origins = "*")
public class SmartHomeController {

    public SmartHome smartHome = new SmartHome(10,25,false);
    private final DeviceService deviceService;

    public SmartHomeController(com.smarthome.DeviceService deviceService) {
        this.deviceService = deviceService;
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

    // ========================================================================
    //POST REQUESTS
    // ========================================================================

    @PostMapping
    public String addDevice(@RequestBody Device device) {
        smartHome.addDevice(device);
        deviceService.addDevice(device);
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

