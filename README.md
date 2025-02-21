# Smart-Home-Automation

This project implements a smart home automation system using Java using linked list and priority queue, and asynchronous programming for device management and rule execution.  It allows for the creation, control, and monitoring of smart home devices, also power and battery management features along with logging.  A REST API is provided for interaction.

## Docs
[Documentation for the code can be found here](https://duckyies.github.io/Smart-Home-Automation-Java/)

## Setup and Running

1. **Prerequisites:**
    * Ensure you have JDK installed.
    * Install Maven or Gradle.
    * Install MongoDB.


2. **Clone the repository:**

   ```bash
   git clone <repository_url>
   ```

3. **Build the project:** (using Maven)

   ```bash
   mvn clean install
   ```

4. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

(See `SmartHomeController.java` for a complete list and descriptions.)

## Features

* **Device Management:**
    * Create and add devices (with various attributes like type, group, location, power level, and battery information).
    * Turn devices on/off individually or by group, type, or location.
    * Remove devices.
    * Retrieve device information (name, type, status, power consumption, battery level).
* **Power Management:**
    * Monitors overall power consumption.
    * Implements a power reduction strategy to stay below a defined threshold.  This involves turning off lower-priority devices if the threshold is exceeded.
    * Provides real-time power consumption feedback.
* **Battery Management:**
    * Tracks battery levels for battery-powered devices.
    * Issues warnings and errors when battery levels are low.
    * Automatically turns off devices when their battery is depleted.
* **Rule Engine:**
    * Defines and executes rules for automated device control.  Rules can be added dynamically at runtime.
    * Supports various rule types:
        * `flip [deviceId/deviceName]`: Toggles the power state of a device.
        * `turn [deviceId/deviceName] on/off`: Turns a device on or off.
        * `set [deviceId/deviceName] powerLevel`: Sets the power level of a device.
        * `group [groupName] on/off`: Turns all devices in a group on or off.
        * `type [typeName] on/off`: Turns all devices of a type on or off.
        * `location [locationName] on/off`: Turns all devices in a location on or off.
* **Location and Temperature Management:**
    * Manages device locations and their temperatures.
    *  Provides temperature monitoring and alerts.
    *  Intelligent Air Conditioner control: Automatically adjusts the Air Conditioner based on the desired ideal temperature and current location temperature.
* **Logging:**
    * Logs various events to separate files (info, warning, severe, power consumption, battery).  Log levels are configurable.
    * Provides a REST endpoint to retrieve the logs.
* **REST API:**
    * Exposes a RESTful API for interacting with the smart home system (using Spring Boot).
    * Allows for remote device control and data retrieval.
* **Asynchronous Operations:**  Uses `@Async` annotations for non-blocking operations.
* **Data Structures:** Use custom and standard Java data structures for efficient device and task management (LinkedList, PriorityQueue, ConcurrentHashMap).


## Technology Stack

* Java
* Spring Boot (for REST API)
* MongoDB (for persistence)

## Future Enhancements

* **User Authentication and Authorization:**
* **Device Discovery:** 
* **More Sophisticated Rule Engine:**  A
* **Integration with other Smart Home Platforms:**
* **Improved UI:**
* **Real-time data visualization**