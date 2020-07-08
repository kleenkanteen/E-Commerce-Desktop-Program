package controller_presenter_gateway;

import entities.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminLogin {
    private static final Logger logger = Logger.getLogger(AdminLogin.class.getName());
    private static final Handler consoleHandler = new ConsoleHandler();

    /** An arraylist to store all admin objects. **/
    private ArrayList<Admin> adminAccountInformation;
    
    String serializedAdminData = "src/ser_file_infos/serializeAdminData.ser";

    public AdminLogin(String username, String password, HashMap<String, Admin> adminHashMap) throws IOException, ClassNotFoundException {
        run();
    }
    public void run() throws IOException, ClassNotFoundException {
        System.out.println("running");
    }
}
