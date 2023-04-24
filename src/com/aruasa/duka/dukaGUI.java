package com.aruasa.duka;

import com.aruasa.duka.database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class dukaGUI extends JFrame implements ActionListener {
    private JPanel panel1;
    private JPanel welcome;
    private JPanel login;
    private JPanel register;
    private JPanel attendant;
    private JPanel buyer;
    private JButton wbtnAttendant;
    private JButton wbtnBuyer;
    private JButton wbtnCancel;
    private JTextField ltxtUsername;
    private JPasswordField ltxtPassword;
    private JButton lbtnLogin;
    private JButton lbtnRegister;
    private JLabel llblUsername;
    private JLabel llblPassword;
    private JTextField rtxtName;
    private JTextField rtxtUsername;
    private JPasswordField rtxtPassword;
    private JLabel rlblName;
    private JLabel rlblUsername;
    private JLabel rlblPassword;
    private JButton rbtnRegister;
    private JTextField atxtItemName;
    private JTextField atxtItemPrice;
    private JButton abtnAdd;
    private JButton abtnCancel;
    private JLabel alblItemName;
    private JLabel alblItemPrice;
    private JComboBox ComboBox;
    private JButton bbtnOrder;
    private JLabel blblSearch;
    private JButton abtnDelete;
    private JTable table1;
    database db = new database();

    public dukaGUI() {
        this.setTitle("DUKA");
        this.setSize(400, 400);
        this.add(panel1);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        wbtnAttendant.addActionListener(this);
        wbtnCancel.addActionListener(this);
        wbtnBuyer.addActionListener(this);
        lbtnLogin.addActionListener(this);
        lbtnRegister.addActionListener(this);
        rbtnRegister.addActionListener(this);
        abtnCancel.addActionListener(this);
        abtnAdd.addActionListener(this);
        ComboBox.addActionListener(this);
        bbtnOrder.addActionListener(this);
        abtnDelete.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == wbtnAttendant) {
            welcome.setVisible(false);
            login.setVisible(true);
        }
        if (e.getSource() == wbtnBuyer) {
            try {
                db.viewColumn(ComboBox);
                db.table_update(table1);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            welcome.setVisible(false);
            buyer.setVisible(true);
        }
        if (e.getSource() == wbtnCancel) {
            dispose();
        }
        if (e.getSource() == lbtnLogin) {
            String username = ltxtUsername.getText();
            String password = ltxtPassword.getText();
            if (db.login(username, password)) {
                login.setVisible(false);
                attendant.setVisible(true);
            }
        }
        if (e.getSource() == lbtnRegister) {
            login.setVisible(false);
            register.setVisible(true);
        }
        if (e.getSource() == rbtnRegister) {
            String name=rtxtName.getText();
            String username=rtxtUsername.getText();
            String password=rtxtPassword.getText();
            if(db.register(name,username,password)) {
                register.setVisible(false);
                login.setVisible(true);
            }
        }
        if (e.getSource() == abtnCancel) {
            dispose();
        }
        if (e.getSource() == abtnAdd) {
            String item_name = atxtItemName.getText();
            int Item_price = Integer.parseInt(atxtItemPrice.getText());
            if (db.upload(item_name, Item_price)) {
                //
            }
        }
        if (e.getSource()==abtnDelete) {
            try {
                db.deleteRecord(atxtItemName);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource()==bbtnOrder){
            JOptionPane.showMessageDialog(null,"Order of "  +ComboBox.getSelectedItem()+ " successful");
        }
    }
}
