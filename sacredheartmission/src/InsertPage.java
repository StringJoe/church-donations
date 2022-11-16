import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class InsertPage extends JFrame implements ActionListener {
    private JLabel dateLabel = new JLabel("<html>Enter Year-Month-Day (e.g., 2022-01-10)</html>", SwingConstants.CENTER);
    private JLabel moneyLabel = new JLabel("<html>Enter Donation or Cost Amount: </html>", SwingConstants.CENTER);
    private JLabel first = new JLabel("<html>Enter First Name:</html>", SwingConstants.CENTER);
    private JLabel second = new JLabel("<html>Enter Last Name:</html>", SwingConstants.CENTER);
    private JLabel check = new JLabel("<html>Enter Check Number:</html>", SwingConstants.CENTER);

    private TextField firstNameText = new TextField();
    private TextField lastNameText = new TextField();

    private TextField checkNumberText = new TextField();
    private TextField locationText = new TextField();
    private JLabel building = new JLabel("<html>Enter Building Address:</html>", SwingConstants.CENTER);
    JLabel enteredData = new JLabel();
    private TextField idText = new TextField();
    private TextField dateText = new TextField();
    private TextField moneyText = new TextField();

    private JPanel textFields = new JPanel();

    private JPanel optionBar  = new JPanel();

    private ArrayList<String> tables = new ArrayList<String>();
    private ArrayList<String> sqlStatements = new ArrayList<String>();
    private ArrayList<String> columnsNeeded = new ArrayList<String>();
    private JComboBox tableBox;
    // create a insert button for when user wants to get results
    JButton insertButton = new JButton();

    private String id, date, money;
    private String firstName, lastName;

    private String checkNumber;
    private String location;

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

    public void setDate(String date) {this.date = date;}
    public String getDate() {return date;}
    public void setMoney(String money) {this.money = money;}
    public String getMoney() {return money;}

    // get tables from GrabTableData class
    public void setTables() {
        if(tables.size() > 0) {
            System.out.println("REMOVE");
            tables.removeAll(tables);
        }

        for(Map.Entry<String, ArrayList<String>> i: GrabTableData.grabTableData().entrySet()) {
            this.tables.add(i.getKey());
        }
    }

    // return the tables for later use
    public ArrayList<String> getTables() {
        return tables;
    }

    public InsertPage() {
        // set tables so combobox can grab data
        setTables();

        // create the layout of the page
        this.setTitle("Insert Data Page"); // sets title of the this
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // exit out of application when X is clicked
        this.setLayout(new GridLayout(2,1));

        // create the option bar so user can create select query
        optionBar.setBackground(new Color(158, 6, 24));
        optionBar.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 100));

        // add the text fields to the frame
        textFields.setBackground(Color.lightGray);
        textFields.setLayout(new GridLayout(5, 2));

        // create a dropdown menu of all available tables in database
        tableBox = new JComboBox(getTables().toArray());
        tableBox.addActionListener(this);
        optionBar.add(tableBox);

        // create search button
        createInsertButton();


        // create the common data entries
        addCommonLabels();
        createCommonTextFields();

        // add necessary action listeners
        insertButton.addActionListener(this);

        // add list of query options to top of window
        this.add(optionBar);
        this.add(textFields);
        this.pack();
        this.setVisible(true); // makes this visible
        // set image of application to Sacred Heart
        ImageIcon image = new ImageIcon("sacred-heart.png"); // creates an image icon
        this.setIconImage(image.getImage()); // sets the image icon of the this to our new image
    }

    // add the labels common across all database tables
    private void addCommonLabels() {
        dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        moneyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
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
        }

        // add last name to list if it has a value in it
        if(!getLastName().equals("")) {
            sqlStatements.add(getLastName());
            columnsNeeded.add("lastName");
        }
    }

    private void addCheckStatement() {
        setCheckNumber(checkNumberText.getText());
        if(!getCheckNumber().equals("")) {
            sqlStatements.add(getCheckNumber());
            columnsNeeded.add("checkNumber");
        }
    }

    private void addLocationStatement() {
        setLoc(locationText.getText());
        if(!getLoc().equals("")) {
            sqlStatements.add(getLoc());
            columnsNeeded.add("location");
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

        setDate(dateText.getText());
        setMoney(moneyText.getText());

        if(!getDate().equals("")) {
            sqlStatements.add(getDate());
            columnsNeeded.add("date");
        }

        // get money if not empty
        if(!getMoney().equals("")) {
            sqlStatements.add(getMoney());
            columnsNeeded.add(col);
        }
    }

    private void clearAllTextFields() {
        firstNameText.setText("");
        lastNameText.setText("");
        checkNumberText.setText("");
        locationText.setText("");
        idText.setText("");
        moneyText.setText("");
    }

    private void insertData(String table) {
        // remove all the statements previously added to the lists
        sqlStatements.removeAll(sqlStatements);
        columnsNeeded.removeAll(columnsNeeded);

        // add the statements back in so query can be executed
        if(table.equals("cashDonations")) {
            addCommonStatements(table);

        }
        else if(table.equals("checkDonations")) {
            addNameStatements();
            addCommonStatements(table);
            addCheckStatement();
        }
        else if(table.equals("flightInfo")) {
            addNameStatements();
            addCommonStatements(table);
        }
        else if(table.equals("rentedBuilding")) {
            addLocationStatement();
            addCommonStatements(table);
        }

        InsertData insert = new InsertData(table, sqlStatements, "donations.db");
        insert.executeQuery();
        enteredData.setText("<html>Added " + insert + " into " + table + "</html>");

    }

    // create the insert data button
    private void createInsertButton() {
        insertButton.setText("Insert Data");
        insertButton.setSize(50,50);
        optionBar.add(insertButton);

        enteredData.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        enteredData.setForeground(Color.WHITE);
        optionBar.add(enteredData);
    }

    // only money and date are common for data entry
    private void createCommonTextFields(){
        // add date label and text field
        textFields.add(dateLabel);
        dateText.setPreferredSize(new Dimension(70, 20));
        dateText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(dateText);

        // add new panel to text fields group
        textFields.add(moneyLabel);

        // set the prefered size and font of the money text field
        moneyText.setPreferredSize(new Dimension(60, 20));
        moneyText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        textFields.add(moneyText);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==tableBox) {
            if (tableBox.getSelectedItem().equals("checkDonations")) {
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table
                createNameFields();
                createCheckField();
            } else if (tableBox.getSelectedItem().equals("flightInfo")) {
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table
                createNameFields();
            } else if (tableBox.getSelectedItem().equals("cashDonations")) {
                // remove fields before repainting
                removeNameFields();
                removeCheckField();
                removeLocField();

                // add fields for table

            } else if (tableBox.getSelectedItem().equals("rentedBuilding")) {
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

        if(e.getSource()==insertButton) {
            insertData(tableBox.getSelectedItem().toString());
            clearAllTextFields();
        }
    }
}
