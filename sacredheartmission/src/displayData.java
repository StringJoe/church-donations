import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class displayData extends JFrame {
    private JTable sqlTable;

    public displayData(String table, String[] cols, String[] sqlData) {
        // create a new Jframe for the search results
        this.setTitle("Database Search Result");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // create a new table model to store rows of data
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(cols);

        // create a new jtable and automatically have it resize itself
        sqlTable = new JTable();
        sqlTable.setModel(model);
        sqlTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        sqlTable.setFillsViewportHeight(true);

        // create a scroll pane so user can scroll through the data
        JScrollPane scroll = new JScrollPane(sqlTable);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // loop through the queried data
        for(String i: sqlData) {
            if(table.equals("cashDonations")) {
                model.addRow(new Object[]{i.split("/")[0],i.split("/")[1],i.split("/")[2]});
            }
            else if (table.equals("checkDonations")) {
                model.addRow(new Object[]{i.split("/")[0], i.split("/")[1], i.split("/")[2],
                        i.split("/")[3], i.split("/")[4], i.split("/")[5]});
            }
            else if(table.equals("flightInfo")) {
                model.addRow(new Object[]{i.split("/")[0], i.split("/")[1], i.split("/")[2],
                        i.split("/")[3], i.split("/")[4]});
            }
            else if(table.equals("rentedBuilding")) {
                model.addRow(new Object[]{i.split("/")[0], i.split("/")[1], i.split("/")[2],
                        i.split("/")[3], i.split("/")[4]});
            }
        }

        // make the new frame visible to the user
        this.add(scroll);
        this.setVisible(true);
        this.setSize(800, 700);
    }
}
