import javax.swing.*;
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
    private ArrayList<String> sqlStatements = new ArrayList<String>();
    private ArrayList<String> columnsNeeded = new ArrayList<String>();
    private ArrayList<String> comparisonOperators = new ArrayList<String>();

    // create a search button for when user wants to get results
    JButton searchButton = new JButton();

    // create variables for database values and store them
    private String id, date1, date2, money;
    private String firstName, lastName;

    private String checkNumber;
    private String location;

    // create text fields for each value
    private TextField firstNameText = new TextField();
    private TextField lastNameText = new TextField();

    private TextField checkNumberText = new TextField();
    private TextField locationText = new TextField();

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

    SelectPage() {
        // set the tables in database for later use
        setTables();
        // set main options for the program: title, exit button, and layout to grid layout
        this.setTitle("Sacred Heart Mission Financial Information: Selecting Data"); // sets title of the this
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of application when X is clicked
        this.setLayout(new GridLayout(3,1));

        // create the option bar so user can create select query
        optionBar.setBackground(new Color(158, 6, 24));
        optionBar.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));

        // create the option bar so user can create select query
        showResults.setBackground(Color.WHITE);
        showResults.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));


        // create a dropdown menu of all available tables in database
        tableBox = new JComboBox(getTables().toArray());
        tableBox.addActionListener(this);
        optionBar.add(tableBox);
        searchButton.addActionListener(this);

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

    // create the search button
    private void createSearchButton() {
        searchButton.setText("Search Database");
        searchButton.setSize(50,50);
        optionBar.add(searchButton);
    }

    private void queryDatabase(String table) {

        sqlStatements.removeAll(sqlStatements);
        comparisonOperators.removeAll(comparisonOperators);
        columnsNeeded.removeAll(columnsNeeded);
        addNameStatements();
        addCheckStatement();

        // getQueriedData method stopped working suddenly...
        selectData = new SelectData("donations.db", table, sqlStatements, comparisonOperators, columnsNeeded);

        presentData.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        presentData.setText("<html>" + String.valueOf(selectData.executeSelectQuery()).replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");

        showResults.add(presentData);
        this.revalidate();
        this.repaint();
        System.out.println("my data "  + selectData);
    }
    private void createNameFields() {
        firstNameText.setPreferredSize(new Dimension(125,20));
        firstNameText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        lastNameText.setPreferredSize(new Dimension(125,20));
        lastNameText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(firstNameText);
        optionBar.add(lastNameText);
    }
    private void removeNameFields() {
        firstNameText.setText("");
        lastNameText.setText("");
        optionBar.remove(firstNameText);
        optionBar.remove(lastNameText);
    }

    private void createCheckField() {
        checkNumberText.setPreferredSize(new Dimension(100, 20));
        checkNumberText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        optionBar.add(checkNumberText);
    }
    private void removeCheckField() {
        checkNumberText.setText("");
        optionBar.remove(checkNumberText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==tableBox) {
            if(tableBox.getSelectedItem().equals("checkDonations")) {
                removeNameFields();
                removeCheckField();
                createNameFields();
                createCheckField();
            }
            else if(tableBox.getSelectedItem().equals("flightInfo")){
                removeNameFields();
                removeCheckField();
                createNameFields();
            }
            else if (tableBox.getSelectedItem().equals("cashDonations")) {
                removeNameFields();
                removeCheckField();

            }
            else if (tableBox.getSelectedItem().equals("rentedBuilding")){
                removeNameFields();
                removeCheckField();

            }

            optionBar.remove(searchButton);
            createSearchButton();
            optionBar.revalidate();
            optionBar.repaint();

        }
        if(e.getSource()==searchButton) {
            queryDatabase(tableBox.getSelectedItem().toString());
        }
    }

    // override the dimension function so that window will start at size 750x750
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }
}
