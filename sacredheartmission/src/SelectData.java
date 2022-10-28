import java.sql.*;

public class SelectData {
    private String databaseName = "jdbc:sqlite:.\\donations.db";
    private String firstName, lastName, checkNumber, userQuery, table;
    private double donationAmount;

    public SelectData() {
        firstName = null;
        lastName = null;
        checkNumber = null;
        userQuery = null;
        donationAmount = 0;
    }

    // set the variables to appropriate values
    public void setFirstName (String first) {
        this.firstName = first;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

    public void setCheckNumber (String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public void setDonationAmount (double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public void setTable(String table) { this.table = table;}

    // select within a specific date from table
    public void selectDateRange(String date1, String date2) {
        // InvoiceDate BETWEEN '2010-01-01' AND '2010-01-31'
        // create the initial userquery for selecting by date.
        userQuery = String.format("SELECT * from %s WHERE date BETWEEN '%s' AND '%s'", table, date1, date2);

        // if the user enters a first, or last name, or by check number,
        // then it will add to the query string and search by that.
        if(firstName != null) {
            userQuery += String.format(" AND firstName = '%s'", firstName);
        }

        if(lastName != null) {
            userQuery += String.format(" AND lastName = '%s'", lastName);
        }

        if(checkNumber != null) {
            userQuery += String.format(" AND checkNumber = '%s'", checkNumber);
        }

        createDatabaseConnection("Date Range");
    }

    // select by name
    public void selectByName() {
        // name must be wrapped in quotation marks like an actual SQL statement
        userQuery = String.format("SELECT * FROM %s WHERE ", table);

        // select by only first, last, or both names. If no choice is made then select everything
        if (firstName != null && lastName != null) {
            userQuery += String.format(" firstName = '%s' AND lastName = '%s'", firstName, lastName);
        }
        else if (firstName != null && lastName == null) {
            userQuery += String.format("firstName = '%s'", firstName);
        }
        else if (firstName == null && lastName != null) {
            userQuery += String.format("lastName = '%s'", lastName);
        }
        else {
            userQuery = String.format("SELECT * FROM %s;", table);
        }

        createDatabaseConnection("Name");

    }

    // give the user the option to print out all the names table
    public void printAllNames() {
        String fullName = "";
        String id = "";

        try {
            // create connection to the database and then get user query
            Connection conn = DriverManager.getConnection(databaseName);
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT id, firstName, lastName FROM %s", table));
            // print out a header so values can be lined up properly
            System.out.println("---------------------------------");
            System.out.printf("%-10s%s","ID", "Name\n");
            System.out.println("---------------------------------");
            while (rs.next()) {
                // create variables to hold data so it's easier to format in print statement
                fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                id = rs.getString("id");

                // print the formatted data
                System.out.printf("%-10s%s\n", id, fullName);
                System.out.println("---------------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // select by check number
    public void selectByCheckNumber() {
        userQuery = String.format("SELECT * FROM %s WHERE checkNumber = \"%s\"", table, checkNumber);

        createDatabaseConnection("Check #");
    }

    // select donation amount if it's equal to, less than, or greater than amount given
    public void selectDonationAmount(int greaterLessEqual) {
        //userQuery = String.format("SELECT * FROM %s WHERE donationAmount ", table);

        // check if how the user wants to compare amounts and then select by that value
        if(greaterLessEqual == 0) {
            userQuery = String.format("SELECT * FROM %s WHERE donationAmount < '%s'", table, donationAmount);
        }
        else if (greaterLessEqual == 1) {
            userQuery = String.format("SELECT * FROM %s WHERE donationAmount = '%s'", table, donationAmount);
        }
        else if(greaterLessEqual == 2) {
            userQuery = String.format("SELECT * FROM %s WHERE donationAmount > '%s'", table, donationAmount);
        }
        else {
            userQuery = String.format("SELECT * FROM %s", table);
        }

        createDatabaseConnection("Donation Amount");
    }

    public void createDatabaseConnection(String message) {
        // get the query results and pass it into printResults
        try {
            // create connection to the database and then get user query
            Connection conn = DriverManager.getConnection(databaseName);
            Statement statement = conn.createStatement();
            ResultSet queryResults = statement.executeQuery(userQuery);

            // pass results from user query to print function then close connection
            System.out.printf("%s\n", "Retrieving by " + message);
            if(table.equals("checkDonations")){
                PrintData.printCheckResults(queryResults);
            }
            else if(table.equals("cashDonations")){
                PrintData.printCashResults(queryResults);
            }
            else if(table.equals("flightInfo")) {

            }
            else if(table.equals("rentedBuilding")) {

            }
            else {
                System.out.println("No table was selected");
            }


            conn.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
