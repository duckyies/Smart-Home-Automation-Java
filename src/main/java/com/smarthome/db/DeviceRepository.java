package com.smarthome;
import com.smarthome.devices.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
    Device findByDeviceName(String name);
}
