import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class SelectPage extends JFrame implements ActionListener {
    // declare selectData object for connecting to the database
    private SelectData selectData;

    JButton deleteEntryButton = new JButton();
    JButton insertEntryButton = new JButton();
    // create label to present sql results and create table model
    JLabel presentData = new JLabel();
    JButton backToMenu = new JButton();

    JButton clearAllText = new JButton();
    private ArrayList<String> sqlStatements = new ArrayList<String>();
    private ArrayList<String> columnsNeeded = new ArrayList<String>();
    private ArrayList<String> comparisonOperators = new ArrayList<String>();

    // create a search button for when user wants to get results
    JButton searchButton = new JButton();
    JButton writeFileButton = new JButton();

    // create variables for database values and store them
    private String id, date1, date2, money;
    private String firstName, lastName;

    JLabel idLabel = new JLabel("<html>Enter ID:</html>", SwingConstants.CENTER);
    JLabel date1Label = new JLabel("<html>Enter Year-Month-Day (e.g., 2022-01-10)</html>", SwingConstants.CENTER);
    JLabel date2Label = new JLabel("<html>Enter Year-Month-Day (e.g., 2022-09-20)<br>Will search between both dates entered</html>", SwingConstants.CENTER);
    JLabel moneyLabel = new JLabel("<html>Enter Donation or Cost Amount: </html>", SwingConstants.CENTER);
    JPanel moneyPanel = new JPanel();
    JRadioButton greaterSign = new JRadioButton(">");
    JRadioButton lesserSign = new JRadioButton("<");
    JRadioButton equalSign = new JRadioButton("=");
    ButtonGroup signGroup = new ButtonGroup();
    private String storeSign;
    JLabel first = new JLabel("<html>Enter First Name:</html>", SwingConstants.CENTER);
    JLabel second = new JLabel("<html>Enter Last Name:</html>", SwingConstants.CENTER);
    JLabel check = new JLabel("<html>Enter Check Number:</html>", SwingConstants.CENTER);

    JLabel building = new JLabel("<html>Enter Building Address:</html>", SwingConstants.CENTER);

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
    private JPanel textFields = new JPanel();

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

    public void setSign(String sign) {
        this.storeSign = sign;
    }

    public String getSign() {
        return storeSign;
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
        signGroup.add(greaterSign);
        signGroup.add(lesserSign);
        signGroup.add(equalSign);
        // set the tables in database for later use
        setTables();
        // set main options for the program: title, exit button, and layout to grid layout
        this.setTitle("Sacred Heart Mission Financial Information"); // sets title of the this
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application when X is clicked
        this.setLayout(new GridLayout(2,1));

        // create the option bar so user can create select query
        optionBar.setBackground(new Color(158, 6, 24));
        optionBar.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 100));

        // add the text fields to the frame
        textFields.setBackground(Color.lightGray);
        textFields.setLayout(new GridLayout(7, 2));

        // add panel for radio buttons and label to be added to same tile
        moneyPanel.setBackground(Color.lightGray);
        moneyPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // create a dropdown menu of all available tables in database
        tableBox = new JComboBox(getTables().toArray());
        tableBox.addActionListener(this);
        optionBar.add(tableBox);
        createSearchButton();
        createWriteToFileButton();
        createClearFieldsButton();
        createDeleteButton();
        createInsertButton();
        addCommonLabels();
        createCommonTextFields();

        // add the action listeners for necessary components
        searchButton.addActionListener(this);
        writeFileButton.addActionListener(this);
        deleteEntryButton.addActionListener(this);
        insertEntryButton.addActionListener(this);
        clearAllText.addActionListener(this);
        // radio buttons
        greaterSign.addActionListener(this);
        lesserSign.addActionListener(this);
        equalSign.addActionListener(this);

        // add list of query options to top of window
        this.add(optionBar);
        this.add(textFields);
        //this.add(insertPanel);
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

            if(getSign() != null) {
                comparisonOperators.add(getSign());
            }
            else {
                comparisonOperators.add("=");
            }

        }
    }

    // create the search button
    private void createSearchButton() {
        searchButton.setText("Search Database");
        searchButton.setSize(50,50);
        optionBar.add(searchButton);
    }

    private void createWriteToFileButton() {
        writeFileButton.setText("Write Data To File");
        writeFileButton.setSize(50,50);
        optionBar.add(writeFileButton);
    }

    private void createDeleteButton() {
        deleteEntryButton.setText("Delete Entry by ID");
        deleteEntryButton.setSize(50,50);
        optionBar.add(deleteEntryButton);
    }

    private void createInsertButton() {
        insertEntryButton.setText("Enter Data");
        insertEntryButton.setSize(50,50);
        optionBar.add(insertEntryButton);
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
        System.out.println(selectData.getData());
        // display the retrieved sql data in different frame
        new displayData(table, cols, data);

    }

    private void writeToFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        if(!getDate1().equals("")){
            WriteToFile.writeDataLineByLine(getDate1() + "_" + tableBox.getSelectedItem().toString(),
                    getAllCols(tableBox.getSelectedItem().toString()), selectData.returnQuery());
        }
        if(!getDate2().equals("")){
            WriteToFile.writeDataLineByLine(getDate2() + "_" + tableBox.getSelectedItem().toString(),
                    getAllCols(tableBox.getSelectedItem().toString()), selectData.returnQuery());
        }
        else {
            WriteToFile.writeDataLineByLine(dtf.format(now) + "_" + tableBox.getSelectedItem().toString(),
                    getAllCols(tableBox.getSelectedItem().toString()), selectData.returnQuery());
        }

    }

    private void createNameFields() {
        //first.setText("Enter First Name:");
        first.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //first.setForeground(Color.WHITE);

        //second.setText("Enter Last Name:");
        second.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //second.setForeground(Color.WHITE);
        // adjust text field for first name
        firstNameText.setPreferredSize(new Dimension(60,20));
        firstNameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        // adjust text field of last name
        lastNameText.setPreferredSize(new Dimension(60,20));
        lastNameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        // add both to the option bar at top of application
        textFields.add(first);
        textFields.add(firstNameText);
        textFields.add(second);
        textFields.add(lastNameText);
    }
    private void removeNameFields() {
        // reset text for names and remove from option bar so user cannot use them by accident
        firstNameText.setText("");
        lastNameText.setText("");
        textFields.remove(first);
        textFields.remove(second);
        textFields.remove(firstNameText);
        textFields.remove(lastNameText);
    }

    private void createCheckField() {
        //check.setText("Enter Check #:");
        check.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //check.setForeground(Color.WHITE);
        // adjust text field for check and add to option bar
        checkNumberText.setPreferredSize(new Dimension(50, 20));
        checkNumberText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(check);
        textFields.add(checkNumberText);
    }
    private void removeCheckField() {
        // reset the text for the check text field and remove from option panel
        textFields.remove(check);
        checkNumberText.setText("");
        textFields.remove(checkNumberText);
    }

    private void createLocField() {
        //building.setText("Enter Building Address:");
        building.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //building.setForeground(Color.WHITE);
        locationText.setPreferredSize(new Dimension(150, 20));
        locationText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(building);
        textFields.add(locationText);
    }

    private void removeLocField() {
        textFields.remove(building);
        locationText.setText("");
        textFields.remove(locationText);
    }
    private void addCommonLabels() {
        //idLabel.setText("          Enter ID: ");
        idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //idLabel.setForeground(Color.WHITE);

        //date1Label.setText("          Year-Month-Day (2022-01-10): ");
        date1Label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //date1Label.setForeground(Color.WHITE);

        //date2Label.setText("<html>&nbsp;Year-Month-Day (2022-09-20). <br>If entered will search range of dates (e.g. 2022/01/10-2022/09/20):</html>");
        date2Label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //date2Label.setForeground(Color.WHITE);

        //moneyLabel.setText("<html>Dollar Amount <br/>sdfsd:</html>");
        moneyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        //moneyLabel.setForeground(Color.WHITE);
    }
    private void createCommonTextFields(){
        textFields.add(idLabel);

        idText.setPreferredSize(new Dimension(40, 20));
        idText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(idText);

        textFields.add(date1Label);
        date1Text.setPreferredSize(new Dimension(70, 20));
        date1Text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(date1Text);

        textFields.add(date2Label);
        date2Text.setPreferredSize(new Dimension(70, 20));
        date2Text.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(date2Text);

        // add the money label to the new panel
        moneyPanel.add(moneyLabel);

        // set the background color
        greaterSign.setBackground(Color.lightGray);
        lesserSign.setBackground(Color.lightGray);
        equalSign.setBackground(Color.lightGray);

        // add the new radio buttons
        moneyPanel.add(greaterSign);
        moneyPanel.add(lesserSign);
        moneyPanel.add(equalSign);

        // add new panel to text fields group
        textFields.add(moneyPanel);

        // set the preferred size and font of the money text field
        moneyText.setPreferredSize(new Dimension(60, 20));
        moneyText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(moneyText);
    }

    private void deleteData() {
        setId(idText.getText());
        // get the id if not empty
        if(!getId().equals("")) {
            DeleteData delete = new DeleteData(Integer.parseInt(getId()), tableBox.getSelectedItem().toString(), "donations.db");
            delete.deleteData();
        }
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

        if(e.getSource()==greaterSign) {
            setSign(">");
        }
        if(e.getSource()==lesserSign) {
            setSign("<");
        }
        if(e.getSource()==equalSign) {
            setSign("=");
        }

        if(e.getSource()==deleteEntryButton) {
            deleteData();
        }

        if(e.getSource()==insertEntryButton) {
            new InsertPage();
        }

        if(e.getSource()==writeFileButton) {
            writeToFile();
        }
    }

    // override the dimension function so that window will start at size 750x750
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }
}
