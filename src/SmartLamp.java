import java.time.LocalDateTime;

public class SmartLamp extends SmartDevice {

    /**
     SmartLamp is a subclass of SmartDevice that represents a lamp with adjustable brightness and kelvin values.
     */

    private int kelvin;
    private int brightness;

    /**
     Constructs a SmartLamp object with default values of kelvin (4000) and brightness (100).
     @param name the name of the SmartLamp.
     */
    public SmartLamp(String name) {
        super(2, name);
        this.kelvin = 4000;
        this.brightness = 100;
    }

    /**

     Constructs a SmartLamp object with default values of kelvin (4000) and brightness (100).
     @param name the name of the SmartLamp.
     @param initialStatus the initial status of the SmartLamp.
     */
    public SmartLamp(String name, boolean initialStatus) {
        super(2, name, initialStatus);
        this.kelvin = 4000;
        this.brightness = 100;
    }

    /**
     Constructs a SmartLamp object with specified kelvin and brightness values.
     @param name the name of the SmartLamp.
     @param initialStatus the initial status of the SmartLamp.
     @param kelvin the kelvin value of the SmartLamp.
     @param brightness the brightness value of the SmartLamp.
     */
    public SmartLamp(String name, boolean initialStatus, int kelvin, int brightness) {
        super(2, name);
        super.setOnOff(initialStatus);
        this.kelvin = kelvin;
        this.brightness = brightness;
    }

    /**

     Gets the kelvin value of the SmartLamp.
     @return the kelvin value of the SmartLamp.
     */
    public int getKelvin() {
        return kelvin;
    }

    /**

     Sets the kelvin value of the SmartLamp.
     @param kelvin the kelvin value of the SmartLamp.
     */
    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }

    /**
     Gets the brightness value of the SmartLamp.
     @return the brightness value of the SmartLamp.
     */
    public int getBrightness() {
        return brightness;
    }

    /**
     Sets the brightness value of the SmartLamp.
     @param brightness the brightness value of the SmartLamp.
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    /**
     Returns a string representation of the SmartLamp object, including its name, status, kelvin value, brightness value,
     and the time to switch its status (if applicable).
     @param device the SmartLamp object to represent as a string.
     @return a string representation of the SmartLamp object.
     */
    @Override public String toString(SmartDevice device) {
        SmartLamp deviceLamp = (SmartLamp) device;
        String name = device.getName();
        String status = device.getIsOnOff() ? "on" : "off";
        String switchTime;

        if (device.getSwitchTime() != null) {
            LocalDateTime timeNow = device.getSwitchTime();

            switchTime = timeNow.format(DateTimeValidator.getDateTimeFormatter());
        } else {
            switchTime = "null";
        }

        return String.format("Smart Lamp %s is %s and its kelvin value is %dK" +
                        " with %d%c brightness, and its time to switch its status is %s.",
                name, status, deviceLamp.getKelvin(), deviceLamp.getBrightness(), '%', switchTime);
    }
}
