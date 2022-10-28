import org.sqlite.SQLiteConfig;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        checkFileExistence.checkForFile();
        //GrabTableData.grabTableData();

        // use .get to get the key from linkedhashmap and to get index of arraylist
        System.out.println(GrabTableData.grabTableData().get("cashDonations").get(0));

        // THIS IS USED TO GRAB ALL THE KEYS AND VALUES IN HASH MAP
        //for(Map.Entry<String, ArrayList<String>> i: tablesAndColumns.entrySet()) {
        //    System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        //}

    }
}