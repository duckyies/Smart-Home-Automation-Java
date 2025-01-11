package com.smarthome;

public class Main {

    public static void main(String[] args) {
        SmartHome testHome = new SmartHome(10,25);
        System.out.println("Initializing smart home...");
        testHome.initialize();
        testHome.addDevice(testHome.createDevice("Living Room Light", "Necessary", "Lights", "Living Room", true, 100, 0.15, 5000, 1));
        testHome.addDevice(testHome.createDevice("Bedroom Light", "Necessary", "Lights", "Bedroom", true, 100, 0.10, 4000, 1));
        testHome.addDevice(testHome.createDevice("Chandelier", "Decorative", "Lights", "Living Room", true, 100, 0.50, 4500, 1));
        testHome.addDevice(testHome.createDevice("Garden Light", "Decorative", "Lights", "Garden", true, 100, 0.20, 6000, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Living Room)", "Necessary", "Fans", "Living Room", true, 100, 0.30, 10000, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Bedroom)", "Necessary", "Fans", "Bedroom", true, 100, 0.25, 10000, 1));
        testHome.addDevice(testHome.createDevice("Desk Fan", "Necessary", "Fans", "Office", true, 100, 0.20, 5000, 1));
        testHome.addDevice(testHome.createDevice("Smart Alarm Clock", "Health", "Alarms", "Bedroom", true, 100, 0.05, 3000, 0));
        testHome.addDevice(testHome.createDevice("Fire Alarm", "Security", "Alarms", "Living Room", true, 100, 0.03, 3000, 0));
        testHome.addDevice(testHome.createDevice("Doorbell Camera", "Security", "Cameras", "Entrance", true, 100, 0.04, 3000, 0));
        testHome.addDevice(testHome.createDevice("Living Room Camera", "Security", "Cameras", "Living Room", true, 100, 0.05, 100, 0));
        testHome.addDevice(testHome.createDevice("Kitchen Camera", "Security", "Cameras", "Kitchen", true, 100, 0.05, 3000, 0));
        testHome.addDevice(testHome.createDevice("Smart Thermostat", "Necessary", "AirConditioners", "Living Room", true, 0, 0.10, 0, 0));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", false, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));

        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 0, 1.20, 0, 1));
        // Keep the program running
        System.out.println(testHome.getDeviceByLocation("Bedroom", "Bedroom Light"));
        while (true) {
            try {
                    Thread.sleep(1); // Sleep to reduce CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
