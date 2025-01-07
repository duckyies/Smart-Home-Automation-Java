package com.smarthome;

public class Main {

    public static void main(String[] args) {
        SmartHome testHome = new SmartHome();
        System.out.println("Initializing smart home...");
        testHome.initialize();
        testHome.addDevice(testHome.createDevice("Living Room Light", "Necessary", "Lights", "Living Room", true, 100, 0.15, 100, 1));
        testHome.addDevice(testHome.createDevice("Bedroom Light", "Necessary", "Lights", "Bedroom", true, 100, 0.10, 100, 1));
        testHome.addDevice(testHome.createDevice("Chandelier", "Decorative", "Lights", "Living Room", true, 100, 0.50, 100, 1));
        testHome.addDevice(testHome.createDevice("Garden Light", "Decorative", "Lights", "Garden", true, 100, 0.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Living Room)", "Necessary", "Fans", "Living Room", true, 100, 0.30, 1, 1));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Bedroom)", "Necessary", "Fans", "Bedroom", true, 100, 0.25, 100, 1));
        testHome.addDevice(testHome.createDevice("Desk Fan", "Necessary", "Fans", "Office", true, 100, 0.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Smart Alarm Clock", "Health", "Alarms", "Bedroom", true, 100, 0.05, 100, 0));
        testHome.addDevice(testHome.createDevice("Fire Alarm", "Security", "Alarms", "Living Room", true, 100, 0.03, 100, 0));
        testHome.addDevice(testHome.createDevice("Doorbell Camera", "Security", "Cameras", "Entrance", true, 100, 0.04, 100, 0));
        testHome.addDevice(testHome.createDevice("Living Room Camera", "Security", "Cameras", "Living Room", true, 100, 0.05, 100, 0));
        testHome.addDevice(testHome.createDevice("Kitchen Camera", "Security", "Cameras", "Kitchen", true, 100, 0.05, 100, 0));
        testHome.addDevice(testHome.createDevice("Smart Thermostat", "Necessary", "AirConditioners", "Living Room", true, 100, 0.10, 100, 0));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", false, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));

        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 1));
        // Keep the program running
        while (true) {
            try {
                Thread.sleep(1); // Sleep to reduce CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
