import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    /**
     Time class that manages time-related operations for the smart devices.
     */

    private static LocalDateTime initialTime;
    private static LocalDateTime time;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    /**
     * Gets the initial time.
     * @return a string representation of the initial time in the specified format.
     */
    public static String getInitialTime() {
        return initialTime.toString();
    }

    /**
     * Sets the initial time.
     * @param initialTime a string representation of the initial time in the specified format.
     */
    public static void setInitialTime(String initialTime) {
        Time.initialTime = LocalDateTime.parse(initialTime, DATE_TIME_FORMATTER);
    }

    /**
     * Gets the current time.
     * @return the current time as a LocalDateTime object.
     */
    public static LocalDateTime getTime() {
        return Time.time;
    }

    /**
     * Sets the current time and makes necessary switch operations.
     * @param time a string representation of the time to be set in the specified format.
     */
    public static void setTime(String time) {
        LocalDateTime timeParse = LocalDateTime.parse(time, DATE_TIME_FORMATTER);

        if (Time.time != null) {
            if (timeParse.isAfter(Time.time)) {
                Time.time = timeParse;

                if (DeviceList.getSmartDeviceList().size() > 0) {

                    for (SmartDevice device : DeviceList.getSmartDeviceList()) {
                        if (device.getSwitchTime() == null)
                            break;
                        else if (device.getSwitchTime().isAfter(Time.time))
                            break;

                        device.setOnOff(!device.getIsOnOff());
                        device.setSwitchTime("null");

                    }

                    DeviceList.sortList();
                }

            } else if (timeParse.isEqual(Time.time))
                SmartDeviceRunner.addToOutputs("ERROR: There is nothing to change!");
            else
                SmartDeviceRunner.addToOutputs("ERROR: Time cannot be reversed!");
        } else
            Time.time = timeParse;
    }

    /**
     * Skips the specified number of minutes and makes necessary switch operations.
     * @param minuteToAdd the number of minutes to be skipped.
     */
    public static void skipMinutes(int minuteToAdd) {
        if (minuteToAdd > 0) {
            Time.time = Time.time.plusMinutes(minuteToAdd);

            if (DeviceList.getSmartDeviceList().size() > 0) {

                for (SmartDevice device : DeviceList.getSmartDeviceList()) {

                    if (device.getSwitchTime() == null)
                        break;
                    else if (device.getSwitchTime().isAfter(Time.time))
                        break;

                    device.setOnOff(!device.getIsOnOff());
                    device.setSwitchTime("null");

                }

                DeviceList.sortList();
            }
        }
        else if (minuteToAdd == 0)
            SmartDeviceRunner.addToOutputs("ERROR: There is nothing to skip!");
        else
            SmartDeviceRunner.addToOutputs("ERROR: Time cannot be reversed!");
    }

    /**
     * Performs no operation (NOP) by switching on/off smart devices at their switch times.
     */
    public static void nop() {

        if (DeviceList.getSmartDeviceList().size() > 0) {

            if (DeviceList.getSmartDeviceList().get(0).getSwitchTime() != null) {
                Time.time = DeviceList.getSmartDeviceList().get(0).getSwitchTime();

                for (SmartDevice device : DeviceList.getSmartDeviceList()) {

                    if (device.getSwitchTime() == null)
                        break;
                    else if (device.getSwitchTime().isAfter(Time.time))
                        break;

                    device.setOnOff(!device.getIsOnOff());
                    device.setSwitchTime("null");

                }

                DeviceList.sortList();
            } else {
                SmartDeviceRunner.addToOutputs("ERROR: There is nothing to switch!");
            }
        } else
            SmartDeviceRunner.addToOutputs("ERROR: There is nothing to switch!");

    }
}
