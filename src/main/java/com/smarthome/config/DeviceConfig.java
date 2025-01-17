package com.smarthome.config;

import com.smarthome.devices.Device;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating device-related beans.
 */
@Configuration
public class DeviceConfig {

    /**
     * Creates a Device bean.
     *
     * @return a new Device instance
     */
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

    /**
     * Creates a bean for the device name.
     *
     * @return the name of the device
     */
    @Bean
    public String deviceName() {
        return "Test Device";
    }

    /**
     * Creates a bean for the device type.
     *
     * @return the type of the device
     */
    @Bean
    public DeviceType.DeviceTypeEnum deviceType() {
        return DeviceType.DeviceTypeEnum.NECESSARY;
    }

    /**
     * Creates a bean for the device group.
     *
     * @return the group of the device
     */
    @Bean
    public DeviceGroup.DeviceGroupEnum deviceGroup() {
        return DeviceGroup.DeviceGroupEnum.LIGHTS;
    }

    /**
     * Creates a bean for the device location.
     *
     * @return the location of the device
     */
    @Bean
    public DeviceLocation.DeviceLocationEnum deviceLocation() {
        return DeviceLocation.DeviceLocationEnum.BEDROOM;
    }

    /**
     * Creates a bean for the device power status.
     *
     * @return the power status of the device
     */
    @Bean
    public Boolean isTurnedOn() {
        return false;
    }

    /**
     * Creates a bean for the device battery level.
     *
     * @return the battery level of the device
     */
    @Bean
    public double batteryLevel() {
        return 0;
    }

    /**
     * Creates a bean for the device power consumption.
     *
     * @return the power consumption of the device
     */
    @Bean
    public double powerConsumption() {
        return 0.00;
    }

    /**
     * Creates a bean for the device maximum battery capacity.
     *
     * @return the maximum battery capacity of the device
     */
    @Bean
    public int maxBatteryCapacity() {
        return 0;
    }

    /**
     * Creates a bean for the device power level.
     *
     * @return the power level of the device
     */
    @Bean
    public int powerLevel() {
        return 0;
    }
}
