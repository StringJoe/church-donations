import java.io.File;

public class checkFileExistence {
    public static void checkForFile() {
        // create file object to check if file already exists
        File checkFileExistence = new File(".\\donations.db");

        /// check if the file exists. If it does not then create the db file.
        if(!(checkFileExistence.exists())) {
            // call methods to create database and tables
            CreateDatabase.makeDatabase();
            CreateDatabase.createTables();
        }
    }
}
