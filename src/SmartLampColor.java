import java.time.LocalDateTime;

public class SmartLampColor extends SmartLamp {

    /**
     * SmartLampColor is a subclass of SmartLamp that represents a smart lamp that can also change colors.
     */

    private String colorCode;
    private boolean mode;

    /**
     * Constructs a SmartLampColor object with the specified name.
     *
     * @param name the name of the smart lamp
     */
    public SmartLampColor(String name) {
        super(name);
        this.setType(3);
        this.mode = false;
    }

    /**
     * Constructs a SmartLampColor object with the specified name and initial status.
     * @param name          the name of the smart lamp
     * @param initialStatus the initial status of the smart lamp
     */
    public SmartLampColor(String name, boolean initialStatus) {
        super(name, initialStatus);
        this.mode = false;
    }

    /**
     * Constructs a SmartLampColor object with the specified name, initial status, kelvin, and brightness.
     * @param name          the name of the smart lamp
     * @param initialStatus the initial status of the smart lamp
     * @param kelvin        the initial kelvin value of the smart lamp
     * @param brightness    the initial brightness of the smart lamp
     */
    public SmartLampColor(String name, boolean initialStatus, int kelvin, int brightness) {
        super(name, initialStatus, kelvin, brightness);
        this.mode = false;
    }

    /**
     * Constructs a SmartLampColor object with the specified name, initial status, color code, and brightness.
     *
     * @param name          the name of the smart lamp
     * @param initialStatus the initial status of the smart lamp
     * @param colorCode     the initial color code of the smart lamp
     * @param brightness    the initial brightness of the smart lamp
     */
    public SmartLampColor(String name, boolean initialStatus, String colorCode, int brightness) {
        super(name, initialStatus);
        super.setBrightness(brightness);
        this.colorCode = colorCode;
        this.mode = true;
    }

    /**
     * Returns the color code of the smart lamp.
     * @return the color code of the smart lamp
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the color code of the smart lamp.
     * @param colorCode the new color code of the smart lamp
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Returns true if the smart lamp is in color mode, false otherwise.
     * @return true if the smart lamp is in color mode, false otherwise
     */
    public boolean isMode() {
        return mode;
    }

    /**
     * Sets the color mode of the smart lamp.
     * @param mode true to set the smart lamp to color mode, false otherwise
     */
    public void setMode(boolean mode) {
        this.mode = mode;
    }

    /**
     * Returns a string representation of the smart lamp.
     * @param device the SmartDevice object to represent as a string
     * @return a string representation of the smart lamp
     */
    @Override public String toString(SmartDevice device) {
        SmartLampColor deviceLampColor = (SmartLampColor) device;
        String name = device.getName();
        String status = device.getIsOnOff() ? "on" : "off";
        String switchTime;

        if (device.getSwitchTime() != null) {
            LocalDateTime timeNow = device.getSwitchTime();

            switchTime = timeNow.format(DateTimeValidator.getDateTimeFormatter());
        } else {
            switchTime = "null";
        }

        return (deviceLampColor.isMode() ? String.format("Smart Color Lamp %s is %s and its color value" +
                        " is 0x%s with %d%c brightness, and its time to switch its status is %s.",
                name, status, deviceLampColor.getColorCode(),
                deviceLampColor.getBrightness(), '%', switchTime)

                :

                String.format("Smart Color Lamp %s is %s and its color value is %dK" +
                        " with %d%c brightness, and its time to switch its status is %s.",
                name, status, deviceLampColor.getKelvin(),
                deviceLampColor.getBrightness(), '%', switchTime));
    }
}
