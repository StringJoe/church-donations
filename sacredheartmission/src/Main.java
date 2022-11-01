import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<String>();

        test.add("Joe");
        test.add("Santay");
        test.add("2022-11-07");
        test.add("7183");
        test.add("200");

        checkFileExistence.checkForFile();
        //GrabTableData.grabTableData();
        DeleteData deleteData;
        InsertData insertData;

        insertData = new InsertData("checkDonations", test);
        insertData.executeQuery();
        System.out.println(insertData);

        //deleteData = new DeleteData(4, "checkDonations");
        //deleteData.deleteData();
        //System.out.println(deleteData);

        // use .get to get the key from linkedhashmap and to get index of arraylist
        //System.out.println(GrabTableData.grabTableData().get("cashDonations").get(0));

        //System.out.println(GrabTableData.grabTableData().get("cashDonations"));
        // THIS IS USED TO GRAB ALL THE KEYS AND VALUES IN HASH MAP
        //for(Map.Entry<String, ArrayList<String>> i: GrabTableData.grabTableData().entrySet()) {
        //    System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        //}

    }
}