import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage implements ActionListener {
    JFrame frame = new JFrame();
    JButton selectButton = new JButton("Click to Select Entry");
    JButton deleteButton = new JButton("Click to Delete Entry");
    JButton insertButton = new JButton("Click to Insert New Data");

    MenuPage() {
        // set the title
        frame.setTitle("Sacred Heart Mission Financial Information: Main Menu");

        // create the select data button
        selectButton.setBounds(200, 100, 200, 60);
        selectButton.setFocusable(false);
        selectButton.addActionListener(this);
        selectButton.setForeground(Color.lightGray);
        selectButton.setBackground(new Color(158, 6, 24));

        // create the delete data button
        deleteButton.setBounds(200, 250, 200, 60);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(this);
        deleteButton.setForeground(Color.lightGray);
        deleteButton.setBackground(new Color(158, 6, 24));

        // create the insert data Button
        insertButton.setBounds(200, 400, 200, 60);
        insertButton.setFocusable(false);
        insertButton.addActionListener(this);
        insertButton.setForeground(Color.lightGray);
        insertButton.setBackground(new Color(158, 6, 24));

        // add buttons to the frame
        frame.add(selectButton);
        frame.add(deleteButton);
        frame.add(insertButton);

        // set the window settings
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620, 620);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // check which button is pressed and open the appropriate window
        if(e.getSource()==selectButton) {
            frame.dispose();
            SelectPage selectPage = new SelectPage();
        }

        if(e.getSource()==deleteButton){
            frame.dispose();
        }

        if(e.getSource()==insertButton) {
            frame.dispose();
        }
    }
}
