package com.smarthome.config;

import com.smarthome.devices.AirConditioner;
import com.smarthome.enums.DeviceGroup;
import com.smarthome.enums.DeviceLocation;
import com.smarthome.enums.DeviceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating AirConditioner-related beans.
 */
@Configuration
public class AirConditionerConfig {

    /**
     * Creates an AirConditioner bean.
     *
     * @return a new AirConditioner instance
     */
    @Bean
    public AirConditioner airConditioner() {
        return new AirConditioner(
                "Test AC (broken)",
                DeviceType.DeviceTypeEnum.NECESSARY,
                DeviceGroup.DeviceGroupEnum.LIGHTS,
                DeviceLocation.DeviceLocationEnum.BEDROOM,
                false,
                0,
                0.00,
                0,
                0,
                true
        );
    }

}