package models;

import play.db.DB;
import sun.java2d.cmm.Profile;
import sun.rmi.runtime.Log;
import java.util.Date;
import java.sql.*;
import java.util.*;
/**
 * Created by Harsh on 4/13/2017.
 */
public class Login {
    String first_name;
    String pwd;
    Connection con;
    Statement stm;

    public Login()
    {
        con= DB.getConnection();
        try {
            stm=con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void adduser(String grahak_no, String fname, String lname, String address, int phone, String password) {

        String insert = "INSERT INTO userinfo VALUES ('"+grahak_no+"','"+fname+"','"+lname+"','"+address+"','"+phone+"','"+password+"')";
        String insert_grahak = "CREATE TABLE " + grahak_no + " (Bill_id varchar(15), Bill_Month varchar(12), Bill_amount int(12), Paid_status varchar(3), Primary key (Bill_id) )";
        try {
            stm.executeUpdate(insert);
            stm.executeUpdate(insert_grahak);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void add_admin(String username, String fname, String lname, String password) {

        String insert = "INSERT INTO admin VALUES ('"+username+"','"+fname+"','"+lname+"','"+password+"')";
        try {
            stm.executeUpdate(insert);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add_bill(String grahak_no, String Bill_ID, String Bill_Month, String Bill_Amount, String Paid) {

        String insert = "INSERT INTO "+ grahak_no + "  VALUES ('"+Bill_ID+"','"+Bill_Month+"','"+Bill_Amount+"','"+Paid+"')";
        try {
            stm.executeUpdate(insert);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Pay(String user_name, String bill_id) {

        String update = "UPDATE " + user_name +" set Paid_status = 'Yes' WHERE Bill_id = '" + bill_id + "'";
        try {
            stm.executeUpdate(update);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String authuser(String grahak, String password) {
        int check=0;
        String fname = null;
        String query= "SELECT * from userinfo";
        try {
            ResultSet rs= stm.executeQuery(query);
            while(rs.next()){
                first_name = rs.getString("grahak_no");
                pwd = rs.getString("Password");
                if(first_name.equals(grahak) && pwd.equals(password))
                {
                    fname = rs.getString("Firstname");
                    System.out.println(first_name + " " + pwd+ " "+fname);
                    check=1;
                    break;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(check==1){
            return fname;
        }else{
            return "";
        }
    }
    public String auth_admin(String username, String password) {
        int check=0;
        String fname = null;
        String query= "SELECT * from admin";
        try {
            ResultSet rs= stm.executeQuery(query);
            while(rs.next()){
                first_name = rs.getString("username");
                pwd = rs.getString("Password");
                if(first_name.equals(username) && pwd.equals(password))
                {
                    fname = rs.getString("Firstname");
                    System.out.println(first_name + " " + pwd+ " "+fname);
                    check=1;
                    break;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(check==1){
            return fname;
        }else{
            return "";
        }
    }

    public List search_query(String tablename, String bill_id) throws SQLException {

        String query = "SELECT * from " + tablename + " WHERE Bill_id = '"  + bill_id +"'";
        ResultSet rs= stm.executeQuery(query);
        List result_string = new ArrayList();
        while(rs.next())
        {
            result_string.add(rs.getString("Bill_id"));
            result_string.add(rs.getString("Bill_month"));
            result_string.add(rs.getString("Bill_amount"));
            result_string.add(rs.getString("Paid_status"));
            System.out.println("match found");
            System.out.println(result_string);
        }
        return result_string;
    }

}
