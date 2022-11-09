import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<String>();
        ArrayList<String> testComp = new ArrayList<String>();
        ArrayList<String> testCol = new ArrayList<String>();

        // check to make sure file exists
        checkFileExistence.checkForFile();

        //test.add("Samy");
        test.add("2022-09-01");
        test.add("2022-11-07");
        //test.add("Kamkar");
        //test.add("");

        //testCol.add(GrabTableData.grabTableData().get("checkDonations").get(1));
        testCol.add(GrabTableData.grabTableData().get("checkDonations").get(3));
        testCol.add(GrabTableData.grabTableData().get("checkDonations").get(3));
        //testCol.add(GrabTableData.grabTableData().get("checkDonations").get(2));

        //testComp.add("=");
        testComp.add("");
        testComp.add("");
        //testComp.add("=");
        //testComp.add(">");

        MenuPage menuPage = new MenuPage();

        //GrabTableData.grabTableData();
        DeleteData deleteData;
        InsertData insertData;
        SelectData selectData;

        selectData = new SelectData("donations.db", "checkDonations", test, testComp, testCol);
        selectData.executeSelectQuery();
        //System.out.println(selectData);

        //insertData = new InsertData("checkDonations", test, "donations.db");
        //insertData.executeQuery();
        //System.out.println(insertData);

        //deleteData = new DeleteData(3, "checkDonations", "donations.db");
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