package com.smarthome;

import com.smarthome.devices.Device;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class SmartHomeController {

    public SmartHome smartHome = new SmartHome(10,25,false);

    // ========================================================================
    //GET REQUESTS
    // ========================================================================

    @GetMapping
    public Collection<Device> getOnDevices() {
        return smartHome.getPoweredOnDevices();
    }

    @GetMapping("/id/{id}")
    public Device getDeviceByID(@PathVariable String id) {
        return smartHome.getDeviceByID(Integer.parseInt(id));
    }

    @GetMapping("/name/{name}")
    public Device getDeviceByName(@PathVariable String name){
        return smartHome.getDeviceByName(name);
    }

    @GetMapping("/powerConsumption")
    public double getPowerConsumption() {
        return smartHome.getPowerConsumption();
    }

    @GetMapping("/threshold")
    public double getPowerConsumptionThreshold() {
        return smartHome.getThreshold();
    }

    @GetMapping("/idealtemp")
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


    // ========================================================================
    //POST REQUESTS
    // ========================================================================

    @PostMapping
    public String addDevice(@RequestBody Device device) {
        smartHome.addDevice(device);
        return "Device added successfully";
    }


    // ========================================================================
    //PUT REQUESTS
    // ========================================================================

    @PutMapping("/id/{id}/on")
    public String turnOnDeviceByID(@PathVariable String id) {
        smartHome.turnOnDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device turned on successfully";
    }

    @PutMapping("/name/{name}/on")
    public String turnOnDeviceByName(@PathVariable String name) {
        smartHome.turnOnDevice(smartHome.getDeviceByName(name));
        return "Device turned on successfully";
    }

    @PutMapping("/id/{id}/off")
    public String turnOffDeviceByID(@PathVariable String id) {
        smartHome.turnOffDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device turned on successfully";
    }

    @PutMapping("/name/{name}/off")
    public String turnOffDeviceByName(@PathVariable String name) {
        smartHome.turnOffDevice(smartHome.getDeviceByName(name));
        return "Device turned on successfully";
    }

    @DeleteMapping("/id/{id}")
    public String removeDeviceByID(@PathVariable String id) {
        smartHome.removeDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device removed successfully";
    }


    // ========================================================================
    //DELETE REQUESTS
    // ========================================================================


    @DeleteMapping("/name/name}")
    public String removeDeviceByName(@PathVariable String name) {
        smartHome.removeDevice(smartHome.getDeviceByName(name));
        return "Device removed successfully";
    }
}

