import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    public static void makeDatabase() {
        // try to make connection to database
        // and create file if it does not exist
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\donations.db");

            // close the connection
            conn.close();
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " +
                    e.getMessage());
        }
    }
    public static void createTables() {
        try {
            // create the connection to the database and
            // create statement to execute commands
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\donations.db");
            Statement statement = conn.createStatement();

            // create the checkDonations table
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "checkDonations (id INTEGER PRIMARY KEY, firstName TEXT, " +
                    "lastName TEXT, date TEXT, donationAmount INTEGER, checkNumber STRING)");

            /* test values */
            statement.execute("INSERT INTO checkDonations (id, firstName, lastName, " +
                    "date, donationAmount, checkNumber) " +
                    "VALUES(NULL, 'John', 'Hammond', '2022-09-28', " +
                    "50, '1234')");

            statement.execute("INSERT INTO checkDonations (id, firstName, lastName, " +
                    "date, donationAmount, checkNumber) " +
                    "VALUES(NULL, 'Samy', 'Kamkar', '2022-10-05', " +
                    "80, '4321')");

            statement.execute("INSERT INTO checkDonations (id, firstName, lastName, " +
                    "date, donationAmount, checkNumber) " +
                    "VALUES(NULL, 'Kevin', 'Mitnick', '2022-10-12', " +
                    "5, '6789')");

            // create cashDonations table
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "cashDonations (id INTEGER PRIMARY KEY, date TEXT, " +
                    "donationAmount INTEGER)");

            /* test values */
            statement.execute("INSERT INTO cashDonations (id, date, donationAmount) " +
                    "VALUES(NULL, '2022-09-28', 540)");

            statement.execute("INSERT INTO cashDonations (id, date, donationAmount) " +
                    "VALUES(NULL, '2022-10-05', 200)");

            statement.execute("INSERT INTO cashDonations (id, date, donationAmount) " +
                    "VALUES(NULL, '2022-10-12', 375)");

            // create flightInfo table
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "flightInfo (id INTEGER PRIMARY KEY, firstName TEXT, " +
                    "lastName TEXT, date TEXT, flightCost INTEGER)");

            /* test values */
            statement.execute("INSERT INTO flightInfo (id, firstName, " +
                    "lastName, date, flightCost) VALUES (NULL, 'Elon', 'Musk', " +
                    "'2022-09-28', 1500)");

            statement.execute("INSERT INTO flightInfo (id, firstName, " +
                    "lastName, date, flightCost) VALUES (NULL, 'Jeff', 'Bezos', " +
                    "'2022-10-05', 1250)");

            statement.execute("INSERT INTO flightInfo (id, firstName, " +
                    "lastName, date, flightCost) VALUES (NULL, 'Mark', 'Zuckerberg', " +
                    "'2022-10-05', 1000)");

            // create rentedBuilding table
            statement.execute("CREATE TABLE IF NOT EXISTS " +
                    "rentedBuilding (id INTEGER PRIMARY KEY, location TEXT, " +
                    "date TEXT, rentalCost INTEGER)");

            /* test values */
            statement.execute("INSERT INTO rentedBuilding (id, location, date, rentalCost) " +
                    "VALUES(NULL, 'CIA', '2022-09-28', 100000)");

            statement.execute("INSERT INTO rentedBuilding (id, location, date, rentalCost) " +
                    "VALUES(NULL, 'FBI', '2022-10-05', 75000)");

            statement.execute("INSERT INTO rentedBuilding (id, location, date, rentalCost) " +
                    "VALUES(NULL, 'NSA', '2022-10-12', 50000)");

            // close the statement and connection
            statement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " +
                    e.getMessage());
        }
    }

}