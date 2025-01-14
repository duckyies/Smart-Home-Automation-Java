package com.smarthome;

import com.smarthome.devices.Device;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/devices")
public class SmartHomeController {

    public SmartHome smartHome = new SmartHome(10,25,false);

    @GetMapping
    public Collection<Device> getOnDevices() {
        smartHome.addDevice(smartHome.createDevice("Living Room Light", DeviceType.DeviceTypeEnum.DECORATIVE, DeviceGroup.DeviceGroupEnum.LIGHTS, DeviceLocation.DeviceLocationEnum.LIVINGROOM, true, 100, 0.15, 5000, 1));
        return smartHome.getPoweredOnDevices();
    }

    @PostMapping
    public String addDevice(@RequestBody Device device) {
        smartHome.addDevice(device);
        return "Device added successfully";
    }

    @PutMapping("/{id}/toggle")
    public String toggleDevice(@PathVariable String id) {
        smartHome.turnOnDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device toggled successfully";
    }

    @DeleteMapping("/{id}")
    public String removeDevice(@PathVariable String id) {
        smartHome.removeDevice(smartHome.getDeviceByID(Integer.parseInt(id)));
        return "Device removed successfully";
    }
}

