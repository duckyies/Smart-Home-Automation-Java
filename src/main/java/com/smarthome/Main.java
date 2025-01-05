package com.smarthome;

public class Main {

    public static void main(String[] args) {
        SmartHome testHome = new SmartHome();
        System.out.println("Initializing smart home...");
        testHome.initialize();

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
