import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class SelectPage extends JFrame implements ActionListener {
    // declare selectData object for connecting to the database
    private SelectData selectData;
    // create label to present sql results and create table model
    JLabel presentData = new JLabel();
    JButton backToMenu = new JButton();

    JButton clearAllText = new JButton();
    private ArrayList<String> sqlStatements = new ArrayList<String>();
    private ArrayList<String> columnsNeeded = new ArrayList<String>();
    private ArrayList<String> comparisonOperators = new ArrayList<String>();

    // create a search button for when user wants to get results
    JButton searchButton = new JButton();

    // create variables for database values and store them
    private String id, date1, date2, money;
    private String firstName, lastName;

    JLabel idLabel = new JLabel();
    JLabel date1Label = new JLabel();
    JLabel date2Label = new JLabel();
    JLabel moneyLabel = new JLabel();
    JLabel first = new JLabel();
    JLabel second = new JLabel();
    JLabel check = new JLabel();

    JLabel building = new JLabel();

    private String checkNumber;
    private String location;

    // create text fields for each value
    private TextField firstNameText = new TextField();
    private TextField lastNameText = new TextField();

    private TextField checkNumberText = new TextField();
    private TextField locationText = new TextField();

    private TextField idText = new TextField();
    private TextField date1Text = new TextField();
    private TextField date2Text = new TextField();
    private TextField moneyText = new TextField();

    // variables to set the max height and width of window upon opening
    private static final int PREF_W = 750;
    private static final int PREF_H = 750;

    // variable to hold list of tables in database
    private JComboBox tableBox;

    private JPanel optionBar  = new JPanel();
    private JPanel showResults = new JPanel();

    // create array list to hold tables in database
    private ArrayList<String> tables = new ArrayList<String>();

    // getters and setters for name values
    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    // getters and setters for name values
    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCheckNumber(String check) {
        this.checkNumber = check;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setLoc(String loc) {this.location = loc;}
    public String getLoc() {return location;}

    public void setId(String id) {this.id = id;}
    public String getId() {return id;}
    public void setDate1(String date1) {this.date1 = date1;}
    public String getDate1() {return date1;}
    public void setDate2(String date2) {this.date2 = date2;}
    public String getDate2() {return date2;}
    public void setMoney(String money) {this.money = money;}
    public String getMoney() {return money;}
    public ArrayList<String> getTables() {
        return tables;
    }

    public void setTables() {
        //System.out.println(tables.size());
        if(tables.size() > 0) {
            System.out.println("REMOVE");
            tables.removeAll(tables);
        }

        for(Map.Entry<String, ArrayList<String>> i: GrabTableData.grabTableData().entrySet()) {
            this.tables.add(i.getKey());
            //System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        }
        //this.tables = tables;
    }

    public String[] getAllCols(String table) {
        String [] cols = new String[GrabTableData.grabTableData().get(table).size()];

        for(int i = 0; i < cols.length; i++) {
            cols[i] = GrabTableData.grabTableData().get(table).get(i);
            //System.out.println("KEY: " + i.getKey() + " VALUE " + i.getValue()+", ");
        }
        return cols;
    }

    SelectPage() {

        backToMenu.setText("Return to Main Menu");
        backToMenu.setSize(50,50);
        backToMenu.addActionListener(this);
        optionBar.add(backToMenu);
        // set the tables in database for later use
        setTables();
        // set main options for the program: title, exit button, and layout to grid layout
        this.setTitle("Sacred Heart Mission Financial Information: Selecting Data"); // sets title of the this
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application when X is clicked
        this.setLayout(new GridLayout(2,1));

        // create the option bar so user can create select query
        optionBar.setBackground(new Color(158, 6, 24));
        optionBar.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 100));

        // create the option bar so user can create select query
        showResults.setBackground(Color.WHITE);
        showResults.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));


        // create a dropdown menu of all available tables in database
        tableBox = new JComboBox(getTables().toArray());
        tableBox.addActionListener(this);
        optionBar.add(tableBox);
        createSearchButton();
        createClearFieldsButton();
        addCommonLabels();
        createCommonTextFields();

        searchButton.addActionListener(this);
        clearAllText.addActionListener(this);

        // add list of query options to top of window
        this.add(optionBar);

        this.add(new JScrollPane(showResults));
        this.pack();
        this.setVisible(true); // makes this visible
        // set image of application to Sacred Heart
        ImageIcon image = new ImageIcon("sacred-heart.png"); // creates an image icon
        this.setIconImage(image.getImage()); // sets the image icon of the this to our new image

    }


    // add names to sql query if check or flight table selected
    private void addNameStatements(){
        // get values for first and last name
        setFirstName(firstNameText.getText());
        setLastName((lastNameText.getText()));

        // add first name to list if it has a value in it
        if(!getFirstName().equals("")) {
            sqlStatements.add(getFirstName());
            columnsNeeded.add("firstName");
            comparisonOperators.add("=");
        }

        // add last name to list if it has a value in it
        if(!getLastName().equals("")) {
            sqlStatements.add(getLastName());
            columnsNeeded.add("lastName");
            comparisonOperators.add("=");
        }
    }

    private void addCheckStatement() {
        setCheckNumber(checkNumberText.getText());
        if(!getCheckNumber().equals("")) {
            sqlStatements.add(getCheckNumber());
            columnsNeeded.add("checkNumber");
            comparisonOperators.add("=");
        }
    }

    private void addLocationStatement() {
        setLoc(locationText.getText());
        if(!getLoc().equals("")) {
            sqlStatements.add(getLoc());
            columnsNeeded.add("location");
            comparisonOperators.add("=");
        }
    }

    public void addCommonStatements(String table) {
        String col = "";

        if(table.equals("cashDonations") || table.equals("checkDonations")){
            col = "donationAmount";
        }
        else if(table.equals("flightInfo")) {
            col = "flightCost";
        }
        else if (table.equals("rentedBuilding")) {
            col = "rentalCost";
        }

        setId(idText.getText());
        setDate1(date1Text.getText());
        setDate2(date2Text.getText());
        setMoney(moneyText.getText());

        // get the id if not empty
        if(!getId().equals("")) {
            sqlStatements.add(getId());
            columnsNeeded.add("id");
            comparisonOperators.add("=");
        }

        // check if both dates are empty or full
        if(!getDate1().equals("") && !getDate2().equals("")) {
            sqlStatements.add(getDate1());
            columnsNeeded.add("date");
            comparisonOperators.add("");

            sqlStatements.add(getDate2());
            columnsNeeded.add("date");
            comparisonOperators.add("");
        }
        else if(!getDate1().equals("")) {
            sqlStatements.add(getDate1());
            columnsNeeded.add("date");
            comparisonOperators.add("=");
        }
        else if(!getDate2().equals("")) {
            sqlStatements.add(getDate2());
            columnsNeeded.add("date");
            comparisonOperators.add("=");
        }

        // get money if not empty
        if(!getMoney().equals("")) {
            sqlStatements.add(getMoney());
            columnsNeeded.add(col);
            comparisonOperators.add("=");
        }
    }

    // create the search button
    private void createSearchButton() {
        searchButton.setText("Search Database");
        searchButton.setSize(50,50);
        optionBar.add(searchButton);
    }

    private void createClearFieldsButton() {
        clearAllText.setText("Clear Text");
        clearAllText.setSize(50, 50);
        optionBar.add(clearAllText);
    }

    private void clearAllTextFields() {
        firstNameText.setText("");
        lastNameText.setText("");
        checkNumberText.setText("");
        locationText.setText("");
        idText.setText("");
        date1Text.setText("");
        date2Text.setText("");
        moneyText.setText("");
    }

    private void queryDatabase(String table) {
        // declare new variables for presenting queried data with
        String[] cols;
        JTable tablej;
        JFrame frame1;

        // remove all the statements previously added to the lists
        sqlStatements.removeAll(sqlStatements);
        comparisonOperators.removeAll(comparisonOperators);
        columnsNeeded.removeAll(columnsNeeded);

        // add the statements back in so query can be executed
        addNameStatements();
        addCheckStatement();
        addLocationStatement();
        addCommonStatements(table);

        // get all the columns from the current table
        cols = getAllCols(table);

        // getQueriedData method stopped working suddenly...
        selectData = new SelectData("donations.db", table, sqlStatements, comparisonOperators, columnsNeeded);
        selectData.executeSelectQuery();
        String[] data = selectData.returnQuery(); //new String[selectData.returnQuery().length];

        // display the retrieved sql data in different frame
        new displayData(table, cols, data);

    }
    private void createNameFields() {

        first.setText("Enter First Name:");
        first.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        first.setForeground(Color.WHITE);


        second.setText("Enter Last Name:");
        second.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        second.setForeground(Color.WHITE);
        // adjust text field for first name
        firstNameText.setPreferredSize(new Dimension(125,20));
        firstNameText.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        // adjust text field of last name
        lastNameText.setPreferredSize(new Dimension(125,20));
        lastNameText.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        // add both to the option bar at top of application
        optionBar.add(first);
        optionBar.add(firstNameText);
        optionBar.add(second);
        optionBar.add(lastNameText);
    }
    private void removeNameFields() {
        // reset text for names and remove from option bar so user cannot use them by accident
        firstNameText.setText("");
        lastNameText.setText("");
        optionBar.remove(first);
        optionBar.remove(second);
        optionBar.remove(firstNameText);
        optionBar.remove(lastNameText);
    }

    private void createCheckField() {
        check.setText("Enter Check #:");
        check.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        check.setForeground(Color.WHITE);
        // adjust text field for check and add to option bar
        checkNumberText.setPreferredSize(new Dimension(50, 20));
        checkNumberText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(check);
        optionBar.add(checkNumberText);
    }
    private void removeCheckField() {
        // reset the text for the check text field and remove from option panel
        optionBar.remove(check);
        checkNumberText.setText("");
        optionBar.remove(checkNumberText);
    }

    private void createLocField() {
        building.setText("Enter Building Address:");
        building.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        building.setForeground(Color.WHITE);
        locationText.setPreferredSize(new Dimension(150, 20));
        locationText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(building);
        optionBar.add(locationText);
    }

    private void removeLocField() {
        optionBar.remove(building);
        locationText.setText("");
        optionBar.remove(locationText);
    }
    private void addCommonLabels() {
        idLabel.setText("Enter ID: ");
        idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        idLabel.setForeground(Color.WHITE);

        date1Label.setText("Year-Month-Day (2022-01-10): ");
        date1Label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        date1Label.setForeground(Color.WHITE);

        date2Label.setText("Enter 2nd Date to Search Between Dates: ");
        date2Label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        date2Label.setForeground(Color.WHITE);

        moneyLabel.setText("Dollar Amount: ");
        moneyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        moneyLabel.setForeground(Color.WHITE);
    }
    private void createCommonTextFields(){
        optionBar.add(idLabel);

        idText.setPreferredSize(new Dimension(40, 20));
        idText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(idText);

        optionBar.add(date1Label);
        date1Text.setPreferredSize(new Dimension(70, 20));
        date1Text.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(date1Text);

        optionBar.add(date2Label);
        date2Text.setPreferredSize(new Dimension(70, 20));
        date2Text.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(date2Text);

        optionBar.add(moneyLabel);
        moneyText.setPreferredSize(new Dimension(60, 20));
        moneyText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(moneyText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==tableBox) {
            if(tableBox.getSelectedItem().equals("checkDonations")) {
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table
                createNameFields();
                createCheckField();
            }
            else if(tableBox.getSelectedItem().equals("flightInfo")){
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table
                createNameFields();
            }
            else if (tableBox.getSelectedItem().equals("cashDonations")) {
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table

            }
            else if (tableBox.getSelectedItem().equals("rentedBuilding")){
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table
                createLocField();
            }

            optionBar.revalidate();
            optionBar.repaint();

        }
        if(e.getSource()==searchButton) {
            queryDatabase(tableBox.getSelectedItem().toString());
        }
        if(e.getSource()==backToMenu) {
            this.dispose();
            new MenuPage();
        }
        if(e.getSource()==clearAllText) {
            clearAllTextFields();
        }
    }

    // override the dimension function so that window will start at size 750x750
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }
}
