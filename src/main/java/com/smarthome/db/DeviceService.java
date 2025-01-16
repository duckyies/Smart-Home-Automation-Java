package com.smarthome;

import com.smarthome.devices.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private com.smarthome.DeviceRepository deviceRepository;

    public void addDevice(Device device) {
        deviceRepository.save(device);
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device getDeviceByName(String name) {
        return deviceRepository.findByDeviceName(name);
    }

    public void deleteDevice(String id) {
        deviceRepository.deleteById(id);
    }
}

