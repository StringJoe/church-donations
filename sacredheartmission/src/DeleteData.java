import java.sql.*;

public class DeleteData {
    private int id;
    private String table, userQuery, selectQuery, deletedData, database;

    // set the public id for deleting data
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // get the database being queried
    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabase() {
        return database;
    }

    // set the table to table user wants to delete data from
    public void setTable(String table) {
        this.table = table;
    }

    public String getTable() {
        return table;
    }

    // create the user query
    public void setUserQuery(String table, int id) {
        this.userQuery = String.format("DELETE FROM %s WHERE id = %d", table, id);
    }

    public String getUserQuery() {
        return userQuery;
    }

    // select the data and store it before deleting it
    public void setSelectQuery(String table) {
        this.selectQuery = String.format("SELECT * FROM %s WHERE id = %d", table, getId());
    }

    public String getSelectQuery() {
        return selectQuery;
    }

    // store select query in variable to display to user
    public void setDeletedData(String data) {
        this.deletedData = data;
    }

    public String getDeletedData() {
        return deletedData;
    }

    // create constructor for user to delete data with
    public DeleteData(int id, String table, String database) {
        setId(id);
        setTable(table);
        setUserQuery(table, id);
        setSelectQuery(table);
        setDatabase(database);
    }

    public void deleteData() {
        // start creating the query to the database
        String createSentence = "";

        try {
            // create the connection and sql statement, then execute the query
            Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\" + getDatabase());
            Statement statement = conn.createStatement();
            ResultSet deleteResults = statement.executeQuery(getSelectQuery());

            // after getting result set, loop through data using known columns retrieved from linked hash map
            for(int i = 0; i < GrabTableData.grabTableData().get(getTable()).size(); i++) {
                if ((i+1) != GrabTableData.grabTableData().get(getTable()).size()) {
                    createSentence += deleteResults.getString(GrabTableData.grabTableData().get(getTable()).get(i)) + " | ";
                }
                else {
                    createSentence += deleteResults.getString(GrabTableData.grabTableData().get(getTable()).get(i));
                }
            }

            // set the deleted data string and then delete the data
            setDeletedData(createSentence);
            statement.executeUpdate(getUserQuery());

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
        return "Data deleted: \n" + getDeletedData();
    }
}
