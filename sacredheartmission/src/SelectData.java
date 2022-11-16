import java.sql.*;
import java.util.ArrayList;

public class SelectData {
    // hold array to hold queried data
    private ArrayList<String> listOfData = new ArrayList<String>();
    private String database, userQuery, table, queriedData;
    private ArrayList<String> userInput = new ArrayList<String>();
    private ArrayList<String> comparisonOperator = new ArrayList<String>();
    private ArrayList<String> chosenColumns = new ArrayList<String>();

    // get the database from the user
    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    // set the table to table user wants to retrieve data from
    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    // get the sql query from user
    public void setUserQuery(String query) {
        this.userQuery = query;
    }

    public String getUserQuery() {
        return userQuery;
    }

    public void setUserInput(ArrayList<String> userInput) {
        this.userInput = userInput;
    }

    public ArrayList<String> getUserInput() {
        return userInput;
    }

    // get the comparison operator so user can choose how data is selected
    public void setComparisonOperator(ArrayList<String> comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public ArrayList<String> getComparisonOperator() {
        return comparisonOperator;
    }

    public void setChosenColumns(ArrayList<String> chosenColumns) {
        this.chosenColumns = chosenColumns;
    }

    public ArrayList<String> getChosenColumns() {
        return chosenColumns;
    }

    // create constructor so user data is added right away.
    public SelectData(String database, String table, ArrayList<String> userInput, ArrayList<String> comparisonOperator, ArrayList<String> chosenColumns) {
        setDatabase(database);
        setTable(table);
        setUserInput(userInput);
        setComparisonOperator(comparisonOperator);
        setChosenColumns(chosenColumns);
    }

    // get all the data that was queried
    public void setQueriedData(String data) {
        this.queriedData = data;
    }

    public String getData() {
        return this.queriedData;
    }

    public void createUserQuery() {
        // create the beginning of query and get size of userInput list
        String query = String.format("SELECT * FROM %s", getTable());
        int listLength = getUserInput().size();
        int dateCount = 0;
        int addDate = 0;

        for(String i : getChosenColumns()) {
            if(i.equals("date")) {
                dateCount++;
            }
        }

        // loop through the length of the list
        if (listLength > 0) {
            for (int i = 0; i < listLength; i++) {

                // check to see which statement should be added
                if (i == 0) {
                    query += " WHERE";
                }
                else {
                    // make sure empty string has not been entered
                    if(!getUserInput().get(i).equals("")) {
                        query += " AND";
                    }
                    else {
                        continue;
                    }
                }

                // first check if there are 2 date columns in the string
                // then check the flag to ensure only 2 date statements are entered
                // then check to make sure the current column is also equal to date
                if(dateCount == 2 && addDate < 2 && getChosenColumns().get(i).equals("date")) {
                    // if the flag is still equal to 0 then add BETWEEN to statement otherwise just add the date
                    if (addDate == 0) {
                        query += String.format(" %s BETWEEN '%s'", getChosenColumns().get(i), getUserInput().get(i));
                        addDate++;
                    }
                    else {
                        query += String.format(" '%s'", getUserInput().get(i));
                        addDate++;
                    }
                }
                else {
                    // once date has been added to the string or is never entered, then create statement as normal
                    query += String.format(" %s %s '%s'", getChosenColumns().get(i), getComparisonOperator().get(i), getUserInput().get(i));
                }
            }
            query += ";";
            //System.out.println(query);
        }

        setUserQuery(query);
    }
    public String executeSelectQuery() {
        // start creating the query to the database
        String createSentence = "";
        int listLength = GrabTableData.grabTableData().get(getTable()).size();
        createUserQuery();

        try {
            // create the connection and sql statement, then execute the query
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\" + getDatabase());
            Statement statement = conn.createStatement();

            // create result set
            ResultSet insertResults = statement.executeQuery(getUserQuery());

            // execute select query to show user the result of statement
            // after getting result set, loop through data using known columns retrieved from linked hash map
            String temp = "";

            String curTable = "";
            if(getTable().equals("cashDonations")|| getTable().equals("checkDonations")) {
                curTable = "donationAmount";
            }
            else if(getTable().equals("flightInfo")) {
                curTable = "flightCost";
            }
            else if(getTable().equals("rentedBuilding")) {
                curTable = "rentalCost";
            }

            while(insertResults.next()) {
                for(int i = 0; i < listLength; i++) {
                    if ((i+1) != listLength) {
                        // replace with $ sign if money arrives before end of columns reached
                        if (GrabTableData.grabTableData().get(getTable()).get(i).equals(curTable)) {
                            createSentence += "$" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                            temp += "$" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                            continue;
                            // createSentence += GrabTableData.grabTableData().get(getTable()).get(i).toUpperCase() + ": #" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + " | ";
                        }
                        createSentence += insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                        temp += insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                        //createSentence += GrabTableData.grabTableData().get(getTable()).get(i).toUpperCase() + ": " + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + " | ";

                        //temp += insertResults.getString(String.format("%s", GrabTableData.grabTableData().get(getTable()).get(i))) + "\t\t";
                    }
                    else {
                        if (GrabTableData.grabTableData().get(getTable()).get(i).equals("checkNumber")) {
                            createSentence += "#" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                            temp += "#" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                            continue;
                            // createSentence += GrabTableData.grabTableData().get(getTable()).get(i).toUpperCase() + ": #" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + " | ";
                        }
                        createSentence += "$" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                        temp += "$" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + "/";
                        //createSentence += GrabTableData.grabTableData().get(getTable()).get(i).toUpperCase() + ": " + "$" + insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i));
                        //temp += insertResults.getString(String.format("%s", GrabTableData.grabTableData().get(getTable()).get(i))) + "\t\t";
                        temp  += insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i));
                    }
                }
                listOfData.add(temp);
                temp = "";
                createSentence += "\n\n";
            }

            //System.out.println(createSentence);

            // set the selected data
            setQueriedData(createSentence);

            // close the statement and connection
            statement.close();
            conn.close();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return createSentence;
    }

    public String[] returnQuery() {
        String[] data = new String[listOfData.size()];
        for (int i = 0; i < listOfData.size(); i++) {
            data[i] = listOfData.get(i);
        }

        return data;
    }

    @Override
    public String toString() {
        return "Selected Data: \n" + getData();
    }


}
