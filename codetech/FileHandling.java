import java.io.*;

public class FileHandling {
    public static void main(String[] args) {
        String fileName = "sample.txt";

        // Writing into file
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("Welcome to CODTECH Internship.\n");
            writer.write("Java File Handling Example.\n");
            writer.close();
            System.out.println("Data written successfully.");
        } catch (IOException e) {
            System.out.println("Error while writing file.");
        }

        // Reading from file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            System.out.println("\nFile Content:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error while reading file.");
        }

        // Modifying file (Appending new content)
        try {
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("This line is added later.\n");
            writer.close();
            System.out.println("\nFile modified successfully.");
        } catch (IOException e) {
            System.out.println("Error while modifying file.");
        }
    }
}