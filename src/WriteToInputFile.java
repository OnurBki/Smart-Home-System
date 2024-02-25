import java.io.FileWriter;
import java.io.IOException;

public class WriteToInputFile {

    /**
     The WriteToInputFile class provides a method to write the output of SmartDeviceRunner to a file with the given file name.
     */


    /**
     * Writes the output of SmartDeviceRunner to a file with the given file name.
     * @param fileName the name of the file to write the output to
     */
    public static void writeToFile(String fileName) {

        try {
            FileWriter writer = new FileWriter(fileName);

            for (String line : SmartDeviceRunner.getOutputs()) {
                writer.write(line + "\n");
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
