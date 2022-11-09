import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class SelectPage extends JFrame implements ActionListener {
    int a = 0;
    SelectData selectData;
    private ArrayList<String> sqlStatements = new ArrayList<String>();
    private ArrayList<String> columnsNeeded = new ArrayList<String>();
    private ArrayList<String> comparisonOperators = new ArrayList<String>();

    // create a search button for when user wants to get results
    JButton searchButton = new JButton();

    // create variables to select names and store them
    private String firstName, lastName;
    private TextField firstNameText = new TextField();
    private TextField lastNameText = new TextField();

    // variables to set the max height and width of window upon opening
    private static final int PREF_W = 750;
    private static final int PREF_H = 750;

    // variable to hold list of tables in database
    private JComboBox tableBox;

    // create jpanel for options in top of window
    private JPanel optionBar  = new JPanel();

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
        this.setLayout(new GridLayout(6,1));

        // create the option bar so user can create select query
        optionBar.setBackground(new Color(158, 6, 24));
        optionBar.setLayout(new FlowLayout(FlowLayout.LEFT, 25, 10));


        // create a dropdown menu of all available tables in database
        tableBox = new JComboBox(getTables().toArray());
        tableBox.addActionListener(this);
        optionBar.add(tableBox);
        searchButton.addActionListener(this);

        // add list of query options to top of window
        this.add(optionBar);
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

    // create the search button
    private void createSearchButton() {
        searchButton.setText("Search Database");
        searchButton.setSize(50,50);
        optionBar.add(searchButton);
    }

    private void queryDatabase(String table) {
        a++;
        sqlStatements.removeAll(sqlStatements);
        comparisonOperators.removeAll(comparisonOperators);
        columnsNeeded.removeAll(columnsNeeded);
        addNameStatements();
        selectData = new SelectData("donations.db", table, sqlStatements, comparisonOperators, columnsNeeded);
        selectData.executeSelectQuery();
        System.out.println(selectData);
        //System.out.println(a);
    }
    private void createNameFields() {
        firstNameText.setPreferredSize(new Dimension(125,30));
        firstNameText.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        lastNameText.setPreferredSize(new Dimension(125,30));
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==tableBox) {
            if(tableBox.getSelectedItem().equals("checkDonations") || tableBox.getSelectedItem().equals("flightInfo")) {
                removeNameFields();
                createNameFields();
            }
            else if (tableBox.getSelectedItem().equals("cashDonations")) {
                removeNameFields();

            }
            else if (tableBox.getSelectedItem().equals("rentedBuilding")){
                removeNameFields();

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
