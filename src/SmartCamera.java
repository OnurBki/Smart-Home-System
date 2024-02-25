import java.time.Duration;
import java.time.LocalDateTime;

public class SmartCamera extends SmartDevice{

    /**
     The SmartCamera class represents a camera device that consumes storage space
     depending on the duration of its use. It inherits from the SmartDevice abstract class.
     */

    private final double mbPerMinute;
    private LocalDateTime startWork;
    private LocalDateTime finishWork;
    private double mbConsumed;

    /**
     Constructs a new SmartCamera object with the given name and storage consumption rate per minute.
     @param name the name of the camera device
     @param mbPerMinute the storage consumption rate per minute of device usage
     */
    public SmartCamera(String name, double mbPerMinute) {
        super(1, name);
        this.mbPerMinute = mbPerMinute;
    }

    /**
     Constructs a new SmartCamera object with the given name, storage consumption rate per minute, and initial status.
     @param name the name of the camera device
     @param mbPerMinute the storage consumption rate per minute of device usage
     @param initialStatus the initial status of the device (on or off)
     */
    public SmartCamera(String name, double mbPerMinute, boolean initialStatus) {
        super(1, name);
        this.mbPerMinute = mbPerMinute;
        this.isOnOff = false;

        if (initialStatus)
            this.setOnOff(true);
    }

    /**
     Returns the storage consumption rate per minute of device usage.
     @return the storage consumption rate per minute
     */
    public double getMbPerMinute() {
        return mbPerMinute;
    }

    /**
     Calculates the amount of storage space consumed by the device during the given duration of device usage.
     @param workTime the duration of device usage in minutes
     @return the amount of storage space consumed in MB
     */
    public double calculateMbConsumed(double workTime) {
        return mbPerMinute*workTime;
    }

    /**
     Sets the status of the device to on or off.
     If the device is already on and the status is set to on, an error message is added to the outputs list.
     If the device is already off and the status is set to off, an error message is added to the outputs list.
     If the status is set to on, the starting time of device usage is recorded.
     If the status is set to off, the ending time of device usage is recorded and the amount of storage space consumed
     during the device usage is calculated and added to the total storage space consumed by the device so far.
     @param isOnOff the status of the device (on or off)
     */
    @Override
    public void setOnOff(boolean isOnOff) {
        if (isOnOff && this.getIsOnOff())
            SmartDeviceRunner.addToOutputs("ERROR: This device is already switched on!");
        else if (!isOnOff && !this.getIsOnOff())
            SmartDeviceRunner.addToOutputs("ERROR: This device is already switched off!");
        else if (isOnOff && !this.getIsOnOff()) {
            this.isOnOff = true;
            LocalDateTime timeNow = Time.getTime();
            String timeText = timeNow.format(DateTimeValidator.getDateTimeFormatter());
            startWork = LocalDateTime.parse(timeText, DateTimeValidator.getDateTimeFormatter());

            if (this.getSwitchTime() != null) {
                this.setSwitchTime("null");
            }
        } else {
            this.isOnOff = false;
            LocalDateTime timeNow = Time.getTime();
            String timeText = timeNow.format(DateTimeValidator.getDateTimeFormatter());
            finishWork = LocalDateTime.parse(timeText, DateTimeValidator.getDateTimeFormatter());

            if (this.getSwitchTime() != null) {
                this.setSwitchTime("null");
            }

            Duration duration = Duration.between(startWork, finishWork);
            this.mbConsumed += this.calculateMbConsumed((double) duration.getSeconds()/60);
        }
    }

    /**
     * Returns a string representation of the SmartCamera object, including its name, current status (on or off), amount
     * of storage used so far (excluding current status), and the time at which its status will be switched next (if set).
     * The string is formatted as follows:
     * Smart Camera [name] is [status] and used [mbConsumed] MB of storage so far (excluding current status), and its time
     * to switch its status is [switchTime].
     * @param device the SmartCamera object to be represented as a string
     * @return a string representation of the SmartCamera object
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

        return String.format("Smart Camera %s is %s and used %,.2f MB of storage " +
                        "so far (excluding current status), and its time to switch its status is %s.",
                name, status, mbConsumed, switchTime);
    }
}
