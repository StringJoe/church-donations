import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GrabTableData {
    public static LinkedHashMap<String, ArrayList<String>> grabTableData() {
        // create hash map to hold table and column data
        LinkedHashMap<String, ArrayList<String>> tablesAndColumns = new LinkedHashMap<String, ArrayList<String>>();

        // array list to hold table names so columns can be gotten later
        ArrayList<String> tableList = new ArrayList<String>();

        // array list to hold column data and pass it into hash map as value
        ArrayList<String> columnData;

        // create connection to the database and retrieve table and column values
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\donations.db");
            Statement statement = conn.createStatement();
            ResultSet tableData = statement.executeQuery("SELECT name FROM sqlite_schema " +
                    "WHERE type='table' ORDER BY name;");

            // loop through all the tables and store them in array list
            while(tableData.next()){
                tableList.add(tableData.getString(1));
            }


            // loop through the tableList array and grab the metadata from it
            for(String table: tableList) {
                tableData = statement.executeQuery("SELECT * FROM " + table);
                ResultSetMetaData md = tableData.getMetaData();
                int columnCount = md.getColumnCount();

                // initialize the array list every loop so new data can be passed in
                columnData = new ArrayList<String>();

                // loop up to the length of column count until all columns are added to array
                for(int i = 1; i <= columnCount; i++){
                    columnData.add(md.getColumnName(i));
                }

                // add each table as a key and list as value to hash map
                tablesAndColumns.put(table, columnData);
            }
            // close the connection to the database
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not connect");
        }

        // testing that data can be printed.
        //for(Map.Entry<String, ArrayList<String>> i: tablesAndColumns.entrySet()) {
        //    System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        //}

        // return the hash map so main program can use it
        return tablesAndColumns;
    }
}
