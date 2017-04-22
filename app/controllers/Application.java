package controllers;

import javafx.print.PageOrientation;
import play.*;
import play.mvc.*;

import java.sql.SQLException;
import java.util.*;

import models.*;

import static play.Play.id;

public class Application extends Controller {
    public static String user_name;
    public static String check;
    public static void index() {
        render();
    }
    public static void logout() {
        renderTemplate("Application/index.html");
    }
    public static void login(){ render(); }
    public static void login_admin(){ render(); }
    public static void signup() { render(); }
    public static void signup_admin() { render(); }
    public static void company() { render(); }
    public  static void userdata(String grahak_no,String fname,String lname,String address,int phone,String password){
            Login login = new Login();
            login.adduser(grahak_no,fname,lname,address,phone,password);
            index();

    }
    public  static void userdata_admin(String username,String fname,String lname,String password){
        Login login = new Login();
        login.add_admin(username,fname,lname,password);
        index();

    }
    public static void loguser(String username,String password){
        String  check;
        Login login = new Login();
        user_name = username;
        check = login.authuser(username,password);
        if(check.isEmpty()){
            System.out.println("not");
            String invalid="Incorrect username or password";
            renderTemplate("Application/login.html", invalid);
        }else{
            render(check,username);
        }

    }
    public static void loguser_admin(String username,String password){
        String  check;
        Login login = new Login();
        check = login.auth_admin(username,password);
        if(check.isEmpty()){
            System.out.println("not");
            String invalid="Incorrect username or password";
            renderTemplate("Application/login_admin.html", invalid);
        }else{
            render(check,username);
        }

    }

    public static void log_user(String bill_id,String username, String check) throws SQLException {

        Login login = new Login();
        List result_String = new ArrayList();
        result_String = login.search_query(username, bill_id);
        System.out.println(result_String);
        result_String.add(bill_id);
        if(result_String.isEmpty())
        {
            renderTemplate("Application/loguser.html",username, check);
        }
        else {
           // String id = result_String.get(0).toString();
           // System.out.println(id);
            renderTemplate("Application/loguser.html", result_String, username, check);
        }
    }

    public static void company_add(String grahak_no,String Bill_ID, String Bill_Month, String Bill_Amount, String Paid, String check) throws SQLException {

        Login login = new Login();
        login.add_bill(grahak_no,Bill_ID,Bill_Month,Bill_Amount,Paid);
        renderTemplate("Application/loguser_admin.html",check);

    }
    public static void Pay(String username, String check, String bill_id) throws SQLException {

        Login login = new Login();
        login.Pay(username, bill_id);
        renderTemplate("Application/loguser.html",check,username);

    }
}