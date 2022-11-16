import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    public static void writeDataLineByLine(String fileName, String[] header, String[] data) {
        // create a new file from the date and table being printed
        File file = new File(fileName+".csv");

        try {
            // create a new file writer and put it in csv writer object
            FileWriter outputFile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputFile);

            // write the headers to the Excel file
            writer.writeNext(header);

            // start writing the rows of data to the Excel file
            for (String row: data) {
                writer.writeNext(row.split("/"));
            }

            // close the file
            writer.close();

        }
        catch (IOException e) {
            // print the error message
            e.printStackTrace();
        }
    }
}
