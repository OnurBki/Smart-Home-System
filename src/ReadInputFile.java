import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadInputFile {

    // The ReadInputFile class reads the input file and stores the commands in an ArrayList.

    private FileReader fr;
    private BufferedReader br;
    private Path path;
    private int lines;
    private ArrayList<String> commandList;

    /**
     Constructor for ReadInputFile class.
     Initializes a FileReader and a BufferedReader objects for the input file.
     Calculates the total number of lines in the input file.
     Initializes an ArrayList to store the commands.
     @param fileName the name of the input file
     @throws IOException if an I/O error occurs
     */
    public ReadInputFile(String fileName) throws IOException {
        this.fr = new FileReader(fileName);
        this.br = new BufferedReader(fr);
        this.path = Paths.get(fileName);
        this.lines = (int) Files.lines((java.nio.file.Path) path).count();
        this.commandList = new ArrayList<>();
    }

    /**
     Reads the input file and stores the commands in an ArrayList.
     Ignores empty lines while reading.
     @throws IOException if an I/O error occurs
     */
    public void readFile() throws IOException {
        for (int i = 0; i < lines; i++) {
            String line = br.readLine();

            if (!line.isEmpty())
                commandList.add(line.trim());
        }
    }

    /**
     Getter method for the commandList ArrayList.
     @return the ArrayList containing the commands
     */
    public ArrayList<String> getCommandList() {
        return commandList;
    }
}
