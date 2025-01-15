package com.smarthome.config;

import com.smarthome.devices.Device;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceConfig {

    @Bean
    public Device device() {
        return new Device(
                "Test Device",
                DeviceType.DeviceTypeEnum.NECESSARY,
                DeviceGroup.DeviceGroupEnum.LIGHTS,
                DeviceLocation.DeviceLocationEnum.BEDROOM,
                false,
                0,
                0.00,
                0,
                0
        );
    }

    @Bean
    public String deviceName() {
        return "Test Device";
    }

    @Bean
    public DeviceType.DeviceTypeEnum deviceType() {
        return DeviceType.DeviceTypeEnum.NECESSARY;
    }

    @Bean
    public DeviceGroup.DeviceGroupEnum deviceGroup() {
        return DeviceGroup.DeviceGroupEnum.LIGHTS;
    }

    @Bean
    public DeviceLocation.DeviceLocationEnum deviceLocation() {
        return DeviceLocation.DeviceLocationEnum.BEDROOM;
    }

    @Bean
    public Boolean isTurnedOn() {
        return false;
    }

    @Bean
    public double batteryLevel() {
        return 0;
    }

    @Bean
    public double powerConsumption() {
        return 0.00;
    }

    @Bean
    public int maxBatteryCapacity() {
        return 0;
    }

    @Bean
    public int powerLevel() {
        return 0;
    }
}
