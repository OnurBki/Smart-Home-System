import java.time.Duration;
import java.time.LocalDateTime;

public class SmartPlug extends SmartDevice{

    /**
     * A SmartPlug class that extends the SmartDevice class and represents a smart plug device
     * that can be turned on or off, and measures the power consumption when it is in use.
     */

    private boolean plugInOut;
    private double ampere;
    private LocalDateTime startWork;
    private LocalDateTime finishWork;
    private double watt;

    /**
     * Constructs a SmartPlug object with the given name, and initial status off.
     *
     * @param name the name of the smart plug
     */
    public SmartPlug(String name) {
        super(0, name);
        this.plugInOut = false;
        this.watt = 0;
    }

    /**
     * Constructs a SmartPlug object with the given name and initial status.
     * @param name the name of the smart plug
     * @param initialStatus the initial status of the smart plug
     */
    public SmartPlug(String name, boolean initialStatus) {
        super(0, name, initialStatus);
        this.plugInOut = false;
        this.watt = 0;
    }

    /**
     * Constructs a SmartPlug object with the given name, initial status, and ampere value.
     *
     * @param name the name of the smart plug
     * @param initialStatus the initial status of the smart plug
     * @param ampere the ampere value of the smart plug
     */
    public SmartPlug(String name, boolean initialStatus, double ampere) {
        super(0, name);
        this.plugInOut = true;
        this.ampere = ampere;
        this.watt = 0;
        this.isOnOff = false;

        if (initialStatus)
            this.setOnOff(true);
    }

    /**
     * Returns the status of the plug, whether there is an item plugged in or not.
     *
     * @return true if there is an item plugged in, false otherwise
     */
    public boolean getIsPlugInOut() {
        return plugInOut;
    }

    /**
     * Plugs in an item to the plug with the given ampere value.
     * If the plug is already in use, an error message is printed.
     * If the given ampere value is negative, an error message is printed.
     *
     * @param ampere the ampere value of the item being plugged in
     */
    public void plugIn(double ampere) {
        if (!plugInOut) {
            if (ampere > 0) {
                this.plugInOut = true;
                this.ampere = ampere;

                if (this.getIsOnOff()) {
                    LocalDateTime timeNow = Time.getTime();
                    String timeText = timeNow.format(DateTimeValidator.getDateTimeFormatter());
                    startWork = LocalDateTime.parse(timeText, DateTimeValidator.getDateTimeFormatter());
                }
            } else
                SmartDeviceRunner.addToOutputs("ERROR: Ampere value must be a positive number!");
        }
        else {
            SmartDeviceRunner.addToOutputs("ERROR: There is already an item plugged in to that plug!");
        }
    }

    /**
     * Unplugs the item from the plug and calculates the power consumption for the duration it was in use.
     * If the plug is not in use, an error message is printed.
     */
    public void plugOut() {
        if (plugInOut) {
            this.plugInOut = false;

            if (this.getIsOnOff()) {
                LocalDateTime timeNow = Time.getTime();
                String timeText = timeNow.format(DateTimeValidator.getDateTimeFormatter());
                finishWork = LocalDateTime.parse(timeText, DateTimeValidator.getDateTimeFormatter());
                Duration duration = Duration.between(startWork, finishWork);

                this.watt += this.calculateWatt((double) duration.getSeconds()/(60*60));
            }
        }
        else {
            SmartDeviceRunner.addToOutputs("ERROR: This plug has no item to plug out from that plug!");
        }
    }

    /**

     Returns the ampere value of the device.
     @return the ampere value of the device.
     */
    public double getAmpere() {
        return ampere;
    }

    /**
     Sets the on/off status of the device and calculates the power consumption.
     If the device is already switched on and the user tries to switch it on again, an error message will be displayed.
     If the device is already switched off and the user tries to switch it off again, an error message will be displayed.
     If the device is switched on or off successfully, the on/off status will be updated and the power consumption will be calculated.
     @param isOnOff the on/off status of the device.
     */
    @Override
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

            if (plugInOut) {
                LocalDateTime timeNow = Time.getTime();
                String timeText = timeNow.format(DateTimeValidator.getDateTimeFormatter());
                startWork = LocalDateTime.parse(timeText, DateTimeValidator.getDateTimeFormatter());
            }
        } else {
            this.isOnOff = false;
            LocalDateTime timeNow = Time.getTime();
            String timeText = timeNow.format(DateTimeValidator.getDateTimeFormatter());
            finishWork = LocalDateTime.parse(timeText, DateTimeValidator.getDateTimeFormatter());

            if (this.getSwitchTime() != null) {
                this.setSwitchTime("null");
            }

            if (plugInOut) {
                Duration duration = Duration.between(startWork, finishWork);

                this.watt += this.calculateWatt((double) duration.getSeconds()/(60*60));
            }
        }
    }

    /**

     Calculates the power consumption of the device based on the work time.
     @param workTime the work time of the device in hours.
     @return the power consumption of the device.
     */
    public double calculateWatt(double workTime) {
        return 220*ampere*workTime;
    }

    /**
     Returns the string representation of the device object.
     @param device the device object.
     @return the string representation of the device object.
     */
    @Override public String toString(SmartDevice device) {
        String name = device.getName();
        String status = device.getIsOnOff() ? "on" : "off";
        String switchTime;

        if (device.getSwitchTime() != null) {
            LocalDateTime timeNow = device.getSwitchTime();

            switchTime = timeNow.format(DateTimeValidator.getDateTimeFormatter());
        } else {
            switchTime = "null";
        }

        return String.format("Smart Plug %s is %s and consumed %,.2fW so far" +
                        " (excluding current device), and its time to switch its status is %s.",
                name, status, watt, switchTime);
    }
}
