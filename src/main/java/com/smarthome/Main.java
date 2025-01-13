package com.smarthome;
import com.smarthome.enums.DeviceGroup.DeviceGroupEnum;
import com.smarthome.enums.DeviceLocation.DeviceLocationEnum;
import com.smarthome.enums.DeviceType.DeviceTypeEnum;
public class Main {

    public static void main(String[] args) {
        SmartHome testHome = new SmartHome(10,25 , false);
        System.out.println("Initializing smart home...");
        testHome.addDevice(testHome.createDevice("Living Room Light", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.LIVINGROOM, true, 100, 0.15, 5000, 1));
        testHome.addDevice(testHome.createDevice("Bedroom Light", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.BEDROOM, true, 100, 0.10, 4000, 1));
        testHome.addDevice(testHome.createDevice("Chandelier", DeviceTypeEnum.DECORATIVE, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.LIVINGROOM, true, 100, 0.50, 4500, 1));
        testHome.addDevice(testHome.createDevice("Garden Light", DeviceTypeEnum.DECORATIVE, DeviceGroupEnum.LIGHTS, DeviceLocationEnum.GARDEN, true, 100, 0.20, 6000, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Living Room)", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.FANS, DeviceLocationEnum.LIVINGROOM, true, 100, 0.30, 10000, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Bedroom)", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.FANS, DeviceLocationEnum.BEDROOM, true, 100, 0.25, 10000, 1));
        testHome.addDevice(testHome.createDevice("Desk Fan", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.FANS, DeviceLocationEnum.OFFICE, true, 100, 0.20, 5000, 1));
        testHome.addDevice(testHome.createDevice("Smart Alarm Clock", DeviceTypeEnum.HEALTH, DeviceGroupEnum.ALARMS, DeviceLocationEnum.BEDROOM, true, 100, 0.05, 3000, 0));
        testHome.addDevice(testHome.createDevice("Fire Alarm", DeviceTypeEnum.SECURITY, DeviceGroupEnum.ALARMS, DeviceLocationEnum.LIVINGROOM, true, 100, 0.03, 3000, 0));
        testHome.addDevice(testHome.createDevice("Doorbell Camera", DeviceTypeEnum.SECURITY, DeviceGroupEnum.CAMERAS, DeviceLocationEnum.ENTRANCE, true, 100, 0.04, 3000, 0));
        testHome.addDevice(testHome.createDevice("Living Room Camera", DeviceTypeEnum.SECURITY, DeviceGroupEnum.CAMERAS, DeviceLocationEnum.LIVINGROOM, true, 100, 0.05, 100, 0));
        testHome.addDevice(testHome.createDevice("Kitchen Camera", DeviceTypeEnum.SECURITY, DeviceGroupEnum.CAMERAS, DeviceLocationEnum.KITCHEN, true, 100, 0.05, 3000, 0));
        testHome.addDevice(testHome.createDevice("Living Room AC", DeviceTypeEnum.NECESSARY, DeviceGroupEnum.AIRCONDITIONERS, DeviceLocationEnum.LIVINGROOM, true, 0, 0.20, 0, 1));

        testHome.addRule(testHome.parseRule("group cameras off"));
        testHome.addRule(testHome.parseRule("group lights on"));
        testHome.addRule(testHome.parseRule("group fans off"));
        testHome.addRule(testHome.parseRule("turn chandelier off"));
        testHome.addRule(testHome.parseRule("turn garden light off"));
        testHome.addRule(testHome.parseRule("turn living room light off"));
        testHome.addRule(testHome.parseRule("turn bedroom light on"));
        testHome.addRule(testHome.parseRule("turn ceiling fan (living room) on"));
        testHome.addRule(testHome.parseRule("set ceiling fan (living room) 5"));
        testHome.addRule(testHome.parseRule("set 1 3"));

        while (true) {
            try {
                Thread.sleep(1); // Sleep to reduce CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
