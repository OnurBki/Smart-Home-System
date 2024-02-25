import java.time.LocalDateTime;

public abstract class SmartDevice {

    /**
     * Parent Class for all Smart Devices.
     */

    private int type;
    private String name;
    private LocalDateTime switchTime;
    protected boolean isOnOff;

    /**
     * Constructs a SmartDevice object with the specified type and name.
     * @param type the type of the smart device
     * @param name the name of the smart device
     */
    public SmartDevice(int type, String name) {
        this.type = type;
        this.name = name;
        this.isOnOff = false;
    }

    /**
     * Constructs a SmartDevice object with the specified type, name, and initial status.
     * @param type the type of the smart device
     * @param name the name of the smart device
     * @param initialStatus the initial status of the smart device
     */
    public SmartDevice(int type, String name, boolean initialStatus) {
        this.type = type;
        this.name = name;
        this.isOnOff = false;

        if (initialStatus)
            this.setOnOff(true);
    }

    /**
     * Returns the type of the smart device.
     * @return the type of the smart device
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type of the smart device.
     * @param type the type of the smart device
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the name of the smart device.
     * @return the name of the smart device
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the smart device.
     * @param name the name of the smart device
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the switch time of the smart device.
     * @return the switch time of the smart device
     */
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }

    /**
     * Sets the switch time of the smart device.
     * @param switchTime the switch time of the smart device
     */
    public void setSwitchTime(String switchTime) {
        LocalDateTime switchTimeParse;

        if (!switchTime.equals("null")) {
            switchTimeParse = LocalDateTime.parse(switchTime, DateTimeValidator.getDateTimeFormatter());

            if (!switchTimeParse.isBefore(Time.getTime())) {
                if (this.switchTime != null) {
                    if (switchTimeParse.isAfter(this.switchTime)) {
                        this.switchTime = switchTimeParse;
                    } else if (switchTimeParse.isEqual(this.switchTime))
                        SmartDeviceRunner.addToOutputs("ERROR: There is nothing to change!");
                    else
                        SmartDeviceRunner.addToOutputs("ERROR: Switch time cannot be in the past!");
                } else
                    if (switchTimeParse.equals(Time.getTime())) {
                        this.isOnOff = !this.isOnOff;
                    } else {
                        this.switchTime = switchTimeParse;
                    }
            } else {
                SmartDeviceRunner.addToOutputs("ERROR: Switch time cannot be in the past!");
            }

        }
        else {
            this.switchTime = null;
        }
    }

    /**
     * Returns the status of the smart device.
     * @return the status of the smart device
     */
    public boolean getIsOnOff() {
        return isOnOff;
    }

    /**
     Sets the on/off status of the smart device.
     @param isOnOff a boolean value indicating the on/off status of the device
     */
    public void setOnOff(boolean isOnOff) {
        if (isOnOff && this.getIsOnOff())
            SmartDeviceRunner.addToOutputs("ERROR: This device is already switched on!");
        else if (!isOnOff && !this.getIsOnOff())
            SmartDeviceRunner.addToOutputs("ERROR: This device is already switched off!");
        else if (isOnOff && !this.getIsOnOff()) {
            this.isOnOff = true;

            if (this.getSwitchTime() != null) {
                this.setSwitchTime("null");
            }
        } else {
            this.isOnOff = false;

            if (this.getSwitchTime() != null) {
                this.setSwitchTime("null");
            }
        }
    }

    /**
     Returns the string representation of the smart device object.
     It will be specialized in child classes.
     @param device the smart device object to be represented as a string
     @return the string representation of the smart device object
     */
    public abstract String toString(SmartDevice device);
}
