import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
       // start the program and check if the database file exists
        checkFileExistence checkFile = new checkFileExistence();
        checkFile.checkForFile();
       //new MenuPage();
       // Create the window for the user
       new SelectPage();
    }
}