import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class DeletePage extends JFrame implements ActionListener {
    // create the panel to hold the delete button
    private JPanel deleteButtonPanel  = new JPanel();

    // create panel to hold data entry options
    private JPanel userOptions = new JPanel();

    // create the initial JButton
    private JButton deleteEntryButton = new JButton();

    // create the label and text field for the ID
    private JLabel idLabel = new JLabel("<html>Enter ID:</html>", SwingConstants.CENTER);
    private JTextField idTextField = new JTextField();

    // create a string table variable
    String table;

    private ArrayList<String> tables = new ArrayList<String>();
    private JComboBox tableBox;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public ArrayList<String> getTables() {
        return tables;
    }

    public void setTables() {
        if(tables.size() > 0) {
            tables.removeAll(tables);
        }

        for(Map.Entry<String, ArrayList<String>> i: GrabTableData.grabTableData().entrySet()) {
            this.tables.add(i.getKey());
        }
    }

    public DeletePage() {
        setTables();
        // set the initial jframe information for the current window
        this.setTitle("Delete Page");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1));

        // add the combo box as the first item in top panel
        // create a dropdown menu of all available tables in database
        tableBox = new JComboBox(getTables().toArray());
        tableBox.setPreferredSize(new Dimension(200, 50));
        tableBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tableBox.addActionListener(this);
        deleteButtonPanel.add(tableBox);

        // create the option bar so user can create select query
        deleteButtonPanel.setBackground(new Color(158, 6, 24));
        deleteButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 100));

        // add user option panel to frame
        userOptions.setLayout(new GridLayout(1, 2));

        // call the function to create the delete button and the user options
        createDeleteButton();
        createUserOptions();

        // create action listener for the delete button
        deleteEntryButton.addActionListener(this);

        // add the panels to the frame and make it visible
        this.add(deleteButtonPanel);
        this.add(userOptions);
        this.pack();
        this.setVisible(true);
    }

    // add the necessary constraints to the delete button
    private void createDeleteButton() {
        deleteEntryButton.setText("Delete Entry");
        deleteEntryButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        deleteEntryButton.setPreferredSize(new Dimension(200, 50));
        deleteButtonPanel.add(deleteEntryButton);
    }


    // create the label and text field information
    private void createUserOptions() {
        idLabel.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        idTextField.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        userOptions.add(idLabel);
        userOptions.add(idTextField);
    }

    private void deleteData() {
        //setId(idText.getText());
        // get the id if not empty
        if(!idTextField.getText().equals("")) {
            DeleteData delete = new DeleteData(Integer.parseInt(idTextField.getText()), getTable(), "donations.db");
            delete.deleteData();
            //System.out.println(delete);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==deleteEntryButton) {
            setTable(tableBox.getSelectedItem().toString());
            deleteData();
        }
    }
}
