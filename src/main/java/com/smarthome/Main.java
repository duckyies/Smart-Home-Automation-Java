package com.smarthome;

public class Main {

    public static void main(String[] args) {
        SmartHome testHome = new SmartHome();
        System.out.println("Initializing smart home...");
        testHome.initialize();
        testHome.addDevice(testHome.createDevice("Living Room Light", "Necessary", "Lights", "Living Room", true, 100, 0.15, 100, 100));
        testHome.addDevice(testHome.createDevice("Bedroom Light", "Necessary", "Lights", "Bedroom", true, 100, 0.10, 100, 100));
        testHome.addDevice(testHome.createDevice("Chandelier", "Decorative", "Lights", "Living Room", false, 100, 0.50, 100, 100));
        testHome.addDevice(testHome.createDevice("Garden Light", "Decorative", "Lights", "Garden", false, 100, 0.20, 100, 100));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Living Room)", "Necessary", "Fans", "Living Room", true, 100, 0.30, 100, 100));
        testHome.addDevice(testHome.createDevice("Ceiling Fan (Bedroom)", "Necessary", "Fans", "Bedroom", true, 100, 0.25, 100, 100));
        testHome.addDevice(testHome.createDevice("Desk Fan", "Necessary", "Fans", "Office", false, 100, 0.20, 100, 100));
        testHome.addDevice(testHome.createDevice("Smart Alarm Clock", "Health", "Alarms", "Bedroom", true, 100, 0.05, 100, 100));
        testHome.addDevice(testHome.createDevice("Fire Alarm", "Security", "Alarms", "Living Room", true, 100, 0.03, 100, 100));
        testHome.addDevice(testHome.createDevice("Doorbell Camera", "Security", "Cameras", "Entrance", true, 100, 0.04, 100, 100));
        testHome.addDevice(testHome.createDevice("Living Room Camera", "Security", "Cameras", "Living Room", true, 100, 0.05, 100, 100));
        testHome.addDevice(testHome.createDevice("Kitchen Camera", "Security", "Cameras", "Kitchen", true, 100, 0.05, 100, 100));
        testHome.addDevice(testHome.createDevice("Smart Thermostat", "Necessary", "AirConditioners", "Living Room", true, 100, 0.10, 100, 100));
        testHome.addDevice(testHome.createDevice("Living Room AC", "Necessary", "AirConditioners", "Living Room", true, 100, 1.20, 100, 100));
        testHome.addDevice(testHome.createDevice("Bedroom AC", "Necessary", "AirConditioners", "Bedroom", true, 100, 1.10, 100, 100));
        testHome.addDevice(testHome.createDevice("Room Heater (Living Room)", "Necessary", "Heaters", "Living Room", false, 100, 1.50, 100, 100));
        testHome.addDevice(testHome.createDevice("Room Heater (Bedroom)", "Necessary", "Heaters", "Bedroom", false, 100, 1.40, 100, 100));
        testHome.addDevice(testHome.createDevice("Water Heater", "Necessary", "Heaters", "Bathroom", false, 100, 2.00, 100, 100));
        testHome.addDevice(testHome.createDevice("Refrigerator", "Necessary", "Appliances", "Kitchen", true, 100, 0.90, 100, 100));
        testHome.addDevice(testHome.createDevice("Washing Machine", "Necessary", "Appliances", "Laundry", false, 100, 0.80, 100, 100));
        testHome.addDevice(testHome.createDevice("Microwave Oven", "Necessary", "Appliances", "Kitchen", false, 100, 1.00, 100, 100));
        testHome.addDevice(testHome.createDevice("Coffee Maker", "Necessary", "Appliances", "Kitchen", false, 100, 0.20, 100, 100));
        testHome.addDevice(testHome.createDevice("Dishwasher", "Necessary", "Appliances", "Kitchen", false, 100, 0.75, 100, 100));
        testHome.addDevice(testHome.createDevice("Smart TV", "Entertainment", "Entertainment", "Living Room", false, 100, 0.50, 100, 100));
        testHome.addDevice(testHome.createDevice("Home Theatre", "Entertainment", "Entertainment", "Living Room", false, 100, 0.80, 100, 100));
        testHome.addDevice(testHome.createDevice("Gaming Console", "Entertainment", "Entertainment", "Living Room", false, 100, 0.40, 100, 100));
        testHome.addDevice(testHome.createDevice("Smart Sprinkler System", "Necessary", "Gardening", "Garden", true, 100, 0.10, 100, 100));
        testHome.addDevice(testHome.createDevice("Robot Vacuum Cleaner", "Necessary", "Appliances", "Living Room", false, 100, 0.20, 100, 100));
        testHome.addDevice(testHome.createDevice("Smart Speaker", "Entertainment", "Others", "Living Room", true, 100, 0.05, 100, 100));
        testHome.addDevice(testHome.createDevice("Smart Plug", "Necessary", "Others", "Bedroom", true, 100, 0.02, 100, 100));

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
