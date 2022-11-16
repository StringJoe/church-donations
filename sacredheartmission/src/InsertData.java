import java.sql.*;
import java.util.ArrayList;

public class InsertData {
    private String table, selectData, selectQuery, database;
    private ArrayList<String> dataToInsert = new ArrayList<String>();

    // get the database from the user
    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    // get the name of the table
    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    // the array data to user provided values
    public void setDataToInsert(ArrayList<String> data) {
        this.dataToInsert = data;
    }

    public ArrayList<String> getDataToInsert() {
        return dataToInsert;
    }

    public InsertData(String table, ArrayList<String> data, String database) {
        setTable(table);
        setDataToInsert(data);
        setSelectQuery(table);
        setDatabase(database);
    }

    // set the query for user
    public void setSelectQuery(String table) {
        // SELECT * FROM table ORDER BY column DESC LIMIT 1;
        this.selectQuery = String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", table);
    }

    public String getSelectQuery() {
        return selectQuery;
    }

    // get the select data so user can see what was updated
    public void setSelectData(String data) {
        this.selectData = data;
    }

    public String getSelectData() {
        return selectData;
    }

    private String createUserQuery() {
        // create the beginning of the query and get length of list columns are stored in
        String query = String.format("INSERT INTO %s (", getTable());
        int listLength = GrabTableData.grabTableData().get(getTable()).size();

        // loop through the possible columns that can be inserted into
        for (int i = 0; i < listLength; i++) {
            if ((i+1) != listLength) {
                query += String.format("%s, ", GrabTableData.grabTableData().get(getTable()).get(i));
            }
            else {
                query += String.format("%s", GrabTableData.grabTableData().get(getTable()).get(i));
            }
        }

        // finish beginning query values
        query += ") VALUES(NULL, ";
        //System.out.println("column query " + query);
        // get length of list that needs to be inserted
        listLength = getDataToInsert().size();

        // add these values to query string then return it
        for (int i = 0; i < listLength; i++) {
            if ((i+1) != listLength) {
                query += String.format("'%s', ", getDataToInsert().get(i));
            }
            else {
                query += String.format("'%s'", getDataToInsert().get(i));
            }
        }

        // add last parentheses to query
        query += ")";

        //System.out.println("full query " + query);
        //System.out.println(query);
        return query;
    }

    public void executeQuery() {
        // start creating the query to the database
        String createSentence = "(";
        int listLength = GrabTableData.grabTableData().get(getTable()).size();

        try {
            // create the connection and sql statement, then execute the query
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\" + getDatabase());
            Statement statement = conn.createStatement();

            // insert the data into the database
            statement.executeUpdate(createUserQuery());

            // create result set
            ResultSet insertResults = statement.executeQuery(getSelectQuery());

            // execute select query to show user the result of statement
            // after getting result set, loop through data using known columns retrieved from linked hash map
            for(int i = 0; i < listLength; i++) {
                if ((i+1) != listLength) {
                    createSentence += insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + " | ";
                }
                else {
                    createSentence += insertResults.getString(GrabTableData.grabTableData().get(getTable()).get(i));
                }
            }

            createSentence += ")";
            // set the selected data
            setSelectData(createSentence);

            // close the statement and connection
            statement.close();
            conn.close();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    @Override
    public String toString() {
        return getSelectData();
    }
}
