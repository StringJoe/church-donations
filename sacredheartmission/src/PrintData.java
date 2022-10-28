import java.sql.ResultSet;
import java.sql.SQLException;

public class PrintData {
    public static String printCheckResults(ResultSet rs) {
        int id = 0;
        String fullName = "";
        String date = "";
        String checkNumber = "";
        double donationAmount = 0;
        String checkData = "";

        try {
            // print out a header so values can be lined up properly
            System.out.println("----------------------------------------------------------------------------------------------");
            System.out.printf("%-10s%-20s%-20s%-20s%s", "ID", "Name", "Date", "Check #", "Amount Donated\n");
            System.out.println("----------------------------------------------------------------------------------------------");
            while(rs.next()) {
                // create variables to hold data so it's easier to format in print statement
                id = rs.getInt("id");
                fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                date = rs.getString("date");
                checkNumber = rs.getString("checkNumber");
                donationAmount = rs.getInt("donationAmount");

                // print the formatted data
                checkData += String.format("%-10s%-20s%-20s%-20s$%s\n", id,  fullName, date, checkNumber, donationAmount);
                System.out.printf("%-10s%-20s%-20s%-20s$%s\n", id,  fullName, date, checkNumber, donationAmount);
                System.out.println("----------------------------------------------------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return checkData;
    }

    public static String printCashResults(ResultSet rs) {
        int id = 0;
        String date = "";
        double donationAmount = 0;
        String cashData = "";

        try {
            // print out a header so values can be lined up properly
            System.out.println("----------------------------------------------------");
            System.out.printf("%-10s%-20s%s", "ID", "Date", "Amount Donated\n");
            System.out.println("----------------------------------------------------");
            while(rs.next()) {
                // create variables to hold data so it's easier to format in print statement
                id = rs.getInt("id");
                date = rs.getString("date");
                donationAmount = rs.getInt("donationAmount");

                // print the formatted data
                cashData += String.format("%-10s%-20s$%s\n", id, date, donationAmount);
                System.out.printf("%-10s%-20s$%s\n", id, date, donationAmount);
                System.out.println("----------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cashData;
    }
}
