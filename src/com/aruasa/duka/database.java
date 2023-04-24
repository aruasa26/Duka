package com.aruasa.duka;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class database  {
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/db_caesar_kipkoech151729";

    static final String USER = "root";
    static final String PASSWORD = "";
    private Connection connection;
    private Statement statement;

    //The login function
    public boolean login(String username, String password) {
        boolean success = false;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            String query = "SELECT * FROM tbl_tableattendants WHERE username = '" +
                    username + "' AND password = '" + password + "'";
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            success = rs.next();
            rs.close();

            statement.close();
            connection.close();
            if(success){
                JOptionPane.showMessageDialog(null,"You have logged in successfully.\n Welcome");
            } else{
                JOptionPane.showMessageDialog(null,"Invalid username or password");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException m) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, m);
        }
        return success;
    }
    //The register function
    public boolean register(String names, String username, String password) {
        boolean success = false;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            String query = "INSERT INTO tbl_tableattendants (name," +
                    "username,password) VALUES ('" +
                    names + "', '" +
                    username + "', '" +
                    password + "')";
            System.out.println(query);
            statement.executeUpdate(query);
            statement.close();
            success = true;

            statement.close();
            connection.close();
            if(success){
                JOptionPane.showMessageDialog(null,"Welcome "+username+". Use "+password+" to login now");
            } else{
                JOptionPane.showMessageDialog(null,"");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException m) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, m);
        }
        return success;
    }

    //The upload items to db function
    public boolean upload(String itemName, int itemPrice) {
        boolean success = false;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            String query = "INSERT INTO tbl_tableitems (item_name,item_price) VALUES ('" +
                    itemName + "', " +
                    itemPrice + ")";
            System.out.println(query);
            statement.executeUpdate(query);
            statement.close();
            success = true;
            if(success){
                JOptionPane.showMessageDialog(null,itemName+" has successfully been uploaded to Items.");
            } else{
                JOptionPane.showMessageDialog(null,"Item does not exist!");
            }

            statement.close();
            connection.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException m) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, m);
        }
        return success;
    }


    //Get items from com.aruasa.duka.database using The item name to access it
    public boolean getItemDetails(String itemName) {
        boolean success = false;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            String query = "SELECT * FROM tbl_tableitems WHERE name = '" +
                    itemName + "'";
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            success = rs.next();
            rs.close();

            statement.close();
            connection.close();
            if(success){
                JOptionPane.showMessageDialog(null,"You have retrieved data successfully");
            } else{
                JOptionPane.showMessageDialog(null,"Something's wrong");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException m) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, m);
        }
        return success;
    }

    //View columns in combo box from the com.aruasa.duka.database
    public void viewColumn(JComboBox comboBox) throws SQLException {
        boolean success=false;
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            String query = "SELECT item_name FROM tbl_tableitems;";
            System.out.println(query);
            ResultSet rs = statement.executeQuery(query);

            comboBox.removeAllItems();
            while(rs.next())
            {
                comboBox.addItem(rs.getString(1));
            }
            success = rs.next();
            rs.close();

            statement.close();
            connection.close();
        } catch (SQLException e){
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, e);
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
    //Delete row from the table
    //Takes in a textfield as the parameter
    public void deleteRecord(JTextField txtField) throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection conn = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        String Options[] = {"Yes", "No"};
        int OptionSelection = JOptionPane.showOptionDialog(null,
                "Do you want to delete the record?", "Delete Record",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                Options, Options[1]);
        if (OptionSelection == 0) {
            String itemName = txtField.getText();
            try{
                String sql = "DELETE FROM tbl_tableitems WHERE item_name=?";
                PreparedStatement pst = conn.prepareStatement(sql);

                pst.setString(1, itemName);

                int k = pst.executeUpdate();

                if(k==1){
                    JOptionPane.showMessageDialog(null, "Item has been deleted successfully.", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null, "Error: Item has not been deleted.");
                }

            } catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Error: Record has not been deleted. Try again.");
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, e);

            }


        }
    }
    public void table_update(JTable table1) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

            PreparedStatement insert = connection.prepareStatement("SELECT * FROM tbl_tableitems");
            ResultSet resultSet = insert.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            int col = rsmd.getColumnCount();
            String[] colName = new String[col];
            for (int i = 0; i < col; i++) {
                colName[i] = rsmd.getColumnName(i + 1);
                model.setColumnIdentifiers(colName);
            }
            String a, b, c, d, e;
            while (resultSet.next()) {
                a = resultSet.getString(1);
                b = resultSet.getString(2);
                c = resultSet.getString(3);

                String[] row = {a, b, c};
                model.addRow(row);


            }


        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}