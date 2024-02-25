import java.util.ArrayList;
import java.util.Comparator;

public class DeviceList {

    /**
     This class represents a list of Smart Devices.
     */

    // An ArrayList of Smart Devices.
    private static ArrayList<SmartDevice> smartDeviceList = new ArrayList<>();

    /**
     Returns the ArrayList of Smart Devices.
     @return The ArrayList of Smart Devices.
     */
    public static ArrayList<SmartDevice> getSmartDeviceList() {
        return smartDeviceList;
    }

    /**
     Adds a Smart Device to the list.
     @param device The Smart Device to be added.
     */
    public static void add(SmartDevice device) {
        boolean check = true;

        if (smartDeviceList.size() > 0) {

            for (SmartDevice dvc : smartDeviceList) {

                if (dvc.getName().equals(device.getName())) {
                    check = false;
                    SmartDeviceRunner.addToOutputs("ERROR: There is already a smart device with same name!");
                    break;
                }

            }

            if (check) {
                smartDeviceList.add(device);

                sortList();
            }
        } else {
            smartDeviceList.add(device);

            sortList();
        }

    }

    /**
     Removes a Smart Device from the list.
     @param device The name of the Smart Device to be removed.
     */
    public static void remove(String device) {
        boolean check = true;

        if (smartDeviceList.size() > 0) {
            for (SmartDevice dvc : smartDeviceList) {
                if (dvc.getName().equals(device)) {

                    if (dvc.getIsOnOff()) {
                        dvc.setOnOff(false);
                        DeviceList.sortList();
                    }

                    smartDeviceList.remove(dvc);
                    check = false;

                    SmartDeviceRunner.addToOutputs(String.format("SUCCESS: Information about removed smart device is as follows:\n" +
                            "%s", dvc.toString(dvc)));

                    sortList();
                    break;
                }

            }

            if (check) {
                SmartDeviceRunner.addToOutputs("ERROR: There is not such a device!");
            }
        } else {
            SmartDeviceRunner.addToOutputs("ERROR: There is not such a device!");
        }
    }

    /**
     Sorts the list of Smart Devices based on the switch time.
     */
    public static void sortList() {
        Comparator<SmartDevice> switchTimeComparator = Comparator.comparing(SmartDevice::getSwitchTime,
                Comparator.nullsLast(Comparator.naturalOrder()));

        if (smartDeviceList.size() > 0) {
            smartDeviceList.sort(switchTimeComparator);
        }
    }
}
