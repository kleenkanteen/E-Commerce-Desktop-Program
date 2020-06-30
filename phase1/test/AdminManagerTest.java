
import entities.Admin;
import entities.Message;
import uses_cases.AdminAccountManager;
import uses_cases.AdminLoginVerification;
import uses_cases.AdminMessageManager;

import javax.security.auth.callback.TextOutputCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AdminManagerTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int curr = 0;
        String serializedAdminManagerAccountInfo = "src/managers/serializedAdminInformation.ser";
        AdminAccountManager am = new AdminAccountManager(serializedAdminManagerAccountInfo);

        String input="";
        while (!input.equals("exit")) {
            System.out.println("Type 1 for User Login.\nType 2 for User Account Creation.\nType 3 for Admin Login.\nType 'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("3")) {
                    AdminLoginVerification bone = new AdminLoginVerification(serializedAdminManagerAccountInfo);
                    System.out.println("enter username:");
                    String user = br.readLine();
                    System.out.println("enter password");
                    String pass = br.readLine();
                    // see if valid username and password. if login(user, pass) returns null, invalid login)
                    // if valid login, run demoAdminManager()
                    Admin test = bone.login(user, pass);
                    if (test != null)
                        demoAdminManager();
                    else System.out.println("try again");
                }
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */



    public static void demoAdminManager() throws IOException, ClassNotFoundException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int curr = 0;
        String serializedAdminManagerAccountInfo = "src/managers/serializedAdminInformation.ser";
        AdminAccountManager am = new AdminAccountManager(serializedAdminManagerAccountInfo);

        String serializedAdminManagerMessageInfo = "src/managers/serializedMessages.ser";
        AdminMessageManager am2 = new AdminMessageManager(serializedAdminManagerMessageInfo);

        System.out.println("Initial state:\n");
        System.out.println(am);

        //Deserializes contents of the SER file
        am.readFromFile(serializedAdminManagerAccountInfo);
        System.out.println("Admins from ser:\n");
        System.out.println(am.toString());

        am2.readFromFile(serializedAdminManagerMessageInfo);
        System.out.println("Messages from ser:\n");
        System.out.println(am2.toString());

        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Type 1 to add a new admin.\nType 2 to remove an admin.\nType 3 to add a new admin message.\nType 4 to delete an admin message.\nType 'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("1")) {
                    //adds a new admin to AdminManager am's records
                    System.out.println("enter new admin's username:");
                    String user = br.readLine();
                    System.out.println("enter new admin's password");
                    String pass = br.readLine();
                    am.add(new Admin(user, pass));
                }   else if (input.equals("2")) {
                    System.out.println("enter admin's username you want to delete:");
                    String user = br.readLine();
                    System.out.println("enter admin's password you want to delete:");
                    String pass = br.readLine();
                    am.remove(new Admin(user, pass));
                } else if (input.equals("3")) {
                    System.out.println("enter new system message");
                    String newMessage = br.readLine();
                    am2.add(new Message(newMessage));
                } else if (input.equals("4")) {
                    System.out.println("enter a system message to delete");
                    String deletionMessage = br.readLine();
                    am2.remove(new Message(deletionMessage));
                } else System.out.println("exiting");
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }


            //adds a new admin to AdminManager am's records
            //am.add(new Admin("very cool ", "dude123"));
            //am.add(new Admin("bonehead", "chickennugget"));
            //am.login("Macleord", "superpass");

            //adds a new student to AdminManager am2's records
            //am2.add(new Message("ok moo"));
            //am2.add(new Message("cow head"));
            //am2.remove(new Message("ok moo"));
            //am2.remove(new Message("great work son"));


            System.out.println("Admin Accounts after:");
            System.out.println(am);
            System.out.println("Admin Messages after:");
            System.out.println(am2);

            // Writes the existing Admin objects to file.
            // This data is serialized and written to file as a sequence of bytes.
            am.saveToFile(serializedAdminManagerAccountInfo);
            am2.saveToFile(serializedAdminManagerMessageInfo);
        }
    }
}
