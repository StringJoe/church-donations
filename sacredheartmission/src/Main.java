import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        checkFileExistence.checkForFile();
        //GrabTableData.grabTableData();
        DeleteData deleteData;

        deleteData = new DeleteData(3, "cashDonations");
        deleteData.deleteData();
        System.out.println(deleteData);

        // use .get to get the key from linkedhashmap and to get index of arraylist
        //System.out.println(GrabTableData.grabTableData().get("cashDonations").get(0));

        // THIS IS USED TO GRAB ALL THE KEYS AND VALUES IN HASH MAP
        //for(Map.Entry<String, ArrayList<String>> i: GrabTableData.grabTableData().entrySet()) {
        //    System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        //}

    }
}