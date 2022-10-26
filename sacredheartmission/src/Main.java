import java.io.File;

public class Main {
    public static void main(String[] args) {
        // create file object to check if file already exists
        File checkFileExistence = new File(".\\donations.db");

        /// check if the file exists. If it does not then create the db file.
        if(!(checkFileExistence.exists())) {
            //create variable
            CreateDatabase createDatabase = new CreateDatabase();

            // call methods to create database and tables
            createDatabase.makeDatabase();
            createDatabase.createTables();
        }
        else
        {
            //System.out.println("donations.db already exists");
            //System.out.println(checkFileExistence.exists());
        }
        SelectData selectCheck = new SelectData();
        //SelectCheckData selectCheck = new SelectCheckData();
        selectCheck.setTable("checkDonations");
        //selectCheck.setTable("cashDonations");
        //selectCheck.setTable("flightInfo");
        //selectCheck.setFirstName("John");
        //selectCheck.setLastName("Hammond");
        //selectCheck.setFirstName("Samy");
        //selectCheck.setLastName("Mitnick");
        //selectCheck.setCheckNumber("4321");
        //selectCheck.setDonationAmount(50);

        //selectCheck.selectDonationAmount( 2);
        //selectCheck.selectByName();
        //selectCheck.printAllNames();
        //selectCheck.selectDateRange("2022-09-28", "now");
        //selectCheck.selectByCheckNumber();
    }
}