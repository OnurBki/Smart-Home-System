import java.io.IOException;

public class Main {

    /**
     * This is the main method which takes two arguments as input files and runs the application.
     * It reads the commands from the first input file, executes them using SmartDeviceRunner, and writes the output to the second input file.
     * @param args A string array of command line arguments. args[0] is the name of the first input file, and args[1] is the name of the second input file.
     * @throws IOException if there is an input or output error while reading or writing the files.
     */
    public static void main(String[] args) throws IOException {

        ReadInputFile commandLines = new ReadInputFile(args[0]);
        commandLines.readFile();

        SmartDeviceRunner.commandRunner(commandLines);
        WriteToInputFile.writeToFile(args[1]);

    }
}