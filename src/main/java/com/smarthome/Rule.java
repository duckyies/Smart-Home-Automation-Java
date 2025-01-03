package com.smarthome;

/**
 * The Rule class represents a set of actions to be performed on smart home devices.
 */
public class Rule {

    // ID of the device to which the rule applies
    private int deviceId;
    // Indicates whether to flip the state of the device
    private boolean flipState;
    // Indicates whether to set the power level of the device
    private boolean setPowerLevel;
    // The power level to be set
    private int powerLevel;
    // Indicates whether to turn off the group of devices
    private boolean turnGroupOff;
    // Indicates whether to turn on the group of devices
    private boolean turnGroupOn;
    // Name of the group of devices
    private String groupName;
    // Indicates whether to turn off the type of devices
    private boolean turnTypeOff;
    // Indicates whether to turn on the type of devices
    private boolean turnTypeOn;
    // Name of the type of devices
    private String typeName;
    // Indicates whether to turn off the location of devices
    private boolean turnLocationOff;
    // Indicates whether to turn on the location of devices
    private boolean turnLocationOn;
    // Name of the location of devices
    private String locationName;

    /**
     * Constructor to initialize a new Rule object with the specified attributes.
     *
     * @param deviceId         the ID of the device
     * @param flipState        whether to flip the state of the device
     * @param setPowerLevel    whether to set the power level of the device
     * @param powerLevel       the power level to be set
     * @param turnGroupOff     whether to turn off the group of devices
     * @param turnGroupOn      whether to turn on the group of devices
     * @param groupName        the name of the group of devices
     * @param turnTypeOff      whether to turn off the type of devices
     * @param turnTypeOn       whether to turn on the type of devices
     * @param typeName         the name of the type of devices
     * @param turnLocationOff  whether to turn off the location of devices
     * @param turnLocationOn   whether to turn on the location of devices
     * @param locationName     the name of the location of devices
     */
    public Rule(int deviceId, boolean flipState, boolean setPowerLevel, int powerLevel, boolean turnGroupOff, boolean turnGroupOn, String groupName, boolean turnTypeOff, boolean turnTypeOn, String typeName, boolean turnLocationOff, boolean turnLocationOn, String locationName) {
        this.deviceId = deviceId;
        this.flipState = flipState;
        this.setPowerLevel = setPowerLevel;
        this.powerLevel = powerLevel;
        this.turnGroupOff = turnGroupOff;
        this.turnGroupOn = turnGroupOn;
        this.groupName = groupName;
        this.turnTypeOff = turnTypeOff;
        this.turnTypeOn = turnTypeOn;
        this.typeName = typeName;
        this.turnLocationOff = turnLocationOff;
        this.turnLocationOn = turnLocationOn;
        this.locationName = locationName;
    }

    /**
     * Returns the ID of the device.
     *
     * @return the device ID
     */
    public int getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the ID of the device.
     *
     * @param deviceId the new device ID
     */
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Returns whether to flip the state of the device.
     *
     * @return true if the state should be flipped, false otherwise
     */
    public boolean isFlipState() {
        return flipState;
    }

    /**
     * Sets whether to flip the state of the device.
     *
     * @param flipState the new flip state
     */
    public void setFlipState(boolean flipState) {
        this.flipState = flipState;
    }

    /**
     * Returns whether to set the power level of the device.
     *
     * @return true if the power level should be set, false otherwise
     */
    public boolean isSetPowerLevel() {
        return setPowerLevel;
    }

    /**
     * Sets whether to set the power level of the device.
     *
     * @param setPowerLevel the new set power level
     */
    public void setSetPowerLevel(boolean setPowerLevel) {
        this.setPowerLevel = setPowerLevel;
    }

    /**
     * Returns the power level to be set.
     *
     * @return the power level
     */
    public int getPowerLevel() {
        return powerLevel;
    }

    /**
     * Sets the power level to be set.
     *
     * @param powerLevel the new power level
     */
    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    /**
     * Returns whether to turn off the group of devices.
     *
     * @return true if the group should be turned off, false otherwise
     */
    public boolean isTurnGroupOff() {
        return turnGroupOff;
    }

    /**
     * Sets whether to turn off the group of devices.
     *
     * @param turnGroupOff the new turn group off state
     */
    public void setTurnGroupOff(boolean turnGroupOff) {
        this.turnGroupOff = turnGroupOff;
    }

    /**
     * Returns whether to turn on the group of devices.
     *
     * @return true if the group should be turned on, false otherwise
     */
    public boolean isTurnGroupOn() {
        return turnGroupOn;
    }

    /**
     * Sets whether to turn on the group of devices.
     *
     * @param turnGroupOn the new turn group on state
     */
    public void setTurnGroupOn(boolean turnGroupOn) {
        this.turnGroupOn = turnGroupOn;
    }

    /**
     * Returns the name of the group of devices.
     *
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the name of the group of devices.
     *
     * @param groupName the new group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Returns whether to turn off the type of devices.
     *
     * @return true if the type should be turned off, false otherwise
     */
    public boolean isTurnTypeOff() {
        return turnTypeOff;
    }

    /**
     * Sets whether to turn off the type of devices.
     *
     * @param turnTypeOff the new turn type off state
     */
    public void setTurnTypeOff(boolean turnTypeOff) {
        this.turnTypeOff = turnTypeOff;
    }

    /**
     * Returns whether to turn on the type of devices.
     *
     * @return true if the type should be turned on, false otherwise
     */
    public boolean isTurnTypeOn() {
        return turnTypeOn;
    }

    /**
     * Sets whether to turn on the type of devices.
     *
     * @param turnTypeOn the new turn type on state
     */
    public void setTurnTypeOn(boolean turnTypeOn) {
        this.turnTypeOn = turnTypeOn;
    }

    /**
     * Returns the name of the type of devices.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets the name of the type of devices.
     *
     * @param typeName the new type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Returns whether to turn off the location of devices.
     *
     * @return true if the location should be turned off, false otherwise
     */
    public boolean isTurnLocationOff() {
        return turnLocationOff;
    }

    /**
     * Sets whether to turn off the location of devices.
     *
     * @param turnLocationOff the new turn location off state
     */
    public void setTurnLocationOff(boolean turnLocationOff) {
        this.turnLocationOff = turnLocationOff;
    }

    /**
     * Returns whether to turn on the location of devices.
     *
     * @return true if the location should be turned on, false otherwise
     */
    public boolean isTurnLocationOn() {
        return turnLocationOn;
    }

    /**
     * Sets whether to turn on the location of devices.
     *
     * @param turnLocationOn the new turn location on state
     */
    public void setTurnLocationOn(boolean turnLocationOn) {
        this.turnLocationOn = turnLocationOn;
    }

    /**
     * Returns the name of the location of devices.
     *
     * @return the location name
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the name of the location of devices.
     *
     * @param locationName the new location name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}