package com.smarthome;
import com.smarthome.enums.DeviceGroup.DeviceGroupEnum;
import com.smarthome.enums.DeviceLocation.DeviceLocationEnum;
import com.smarthome.enums.DeviceType.DeviceTypeEnum;
public class Main {

    public static void main(String[] args) {
        SmartHome testHome = new SmartHome(10,25);
        System.out.println("Initializing smart home...");
        testHome.addDevice(testHome.createDevice("Living Room Light", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.LIVING_ROOM, true, 100, 0.15, 5000, 1));
        testHome.addDevice(testHome.createDevice("Bedroom Light", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.BEDROOM, true, 100, 0.10, 4000, 1));
        testHome.addDevice(testHome.createDevice("Chandelier", DeviceTypeEnum.DECORATIVE, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.LIVING_ROOM, true, 100, 0.50, 4500, 1));
        testHome.addDevice(testHome.createDevice("Garden Light", DeviceTypeEnum.DECORATIVE, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.GARDEN, true, 100, 0.20, 6000, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Living Room)", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.FANS, DeviceLocationEnum.LIVING_ROOM, true, 100, 0.30, 10000, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Bedroom)", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.FANS, DeviceLocationEnum.BEDROOM, true, 100, 0.25, 10000, 1));
        testHome.addDevice(testHome.createDevice("Desk Fan", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.FANS, DeviceLocationEnum.OFFICE, true, 100, 0.20, 5000, 1));
        testHome.addDevice(testHome.createDevice("Smart Alarm Clock", DeviceTypeEnum.HEALTH, DeviceGroupEnum.ALARMS, DeviceLocationEnum.BEDROOM, true, 100, 0.05, 3000, 0));
        testHome.addDevice(testHome.createDevice("Fire Alarm", DeviceTypeEnum.SECURITY, DeviceGroupEnum.ALARMS, DeviceLocationEnum.LIVING_ROOM, true, 100, 0.03, 3000, 0));
        testHome.addDevice(testHome.createDevice("Doorbell Camera", DeviceTypeEnum.SECURITY, DeviceGroupEnum.CAMERAS, DeviceLocationEnum.ENTRANCE, true, 100, 0.04, 3000, 0));
        testHome.addDevice(testHome.createDevice("Living Room Camera", DeviceTypeEnum.SECURITY, DeviceGroupEnum.CAMERAS, DeviceLocationEnum.LIVING_ROOM, true, 100, 0.05, 100, 0));
        testHome.addDevice(testHome.createDevice("Kitchen Camera", DeviceTypeEnum.SECURITY, DeviceGroupEnum.CAMERAS, DeviceLocationEnum.KITCHEN, true, 100, 0.05, 3000, 0));
        testHome.addDevice(testHome.createDevice("Living Room AC", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.AIR_CONDITIONERS, DeviceLocationEnum.LIVING_ROOM, true, 0, 0.20, 0, 1));

        while (true) {
            try {
                Thread.sleep(1); // Sleep to reduce CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
