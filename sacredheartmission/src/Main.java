import org.sqlite.SQLiteConfig;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        LinkedHashMap<String, ArrayList<String>> tablesAndColumns = new LinkedHashMap<String, ArrayList<String>>();
        // array list to hold table names so columns can be gotten later
        ArrayList<String> tableList = new ArrayList<String>();
        ArrayList<String> columnData;
        // create file object to check if file already exists
        File checkFileExistence = new File(".\\donations.db");

        /// check if the file exists. If it does not then create the db file.
        if(!(checkFileExistence.exists())) {
            // call methods to create database and tables
            CreateDatabase.makeDatabase();
            CreateDatabase.createTables();
        }
        else
        {
            //System.out.println("donations.db already exists");
            //System.out.println(checkFileExistence.exists());
        }

        // THIS IS USED TO GRAB ALL TABLE NAMES!!!!!!
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\donations.db");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_schema " +
                    "WHERE type='table' ORDER BY name;");

            while(rs.next()){
                tableList.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }


            // THIS FOR LOOP IS USED TO GRAB ALL THE COLUMN NAMES
            for(String table: tableList) {
                rs = statement.executeQuery("SELECT * FROM " + table);
                ResultSetMetaData md = rs.getMetaData();
                int cc = md.getColumnCount();


                columnData = new ArrayList<String>();
                    for(int i = 1; i <= cc; i++){
                        columnData.add(md.getColumnName(i));
                        System.out.println("column name " + md.getColumnName(i));
                    }
                    tablesAndColumns.put(table, columnData);
                System.out.println();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Could not connect");
        }

        // THIS IS USED TO GRAB ALL THE KEYS AND VALUES IN HASH MAP
        for(Map.Entry<String, ArrayList<String>> i: tablesAndColumns.entrySet()) {
            System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        }

    }
}