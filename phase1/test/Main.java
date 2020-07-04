

import uses_cases.AdminLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("Type 1 for User Login.\nType 2 for User Account Creation.\nType 3 for Admin Login.\nType 'exit' to exit the program.");
            try {
                input = br.readLine();
                if (input.equals("3"))
                    new AdminLogin();
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
        //demoAdminManager();
    }

    /*
    public static void demoAdminManager() throws IOException, ClassNotFoundException {

        String serializedAdminData = "src/ser_file_infos/serializeAdminData.ser";
        AdminMenuOptions sm = new AdminMenuOptions(serializedAdminData);
        System.out.println("Initial state:\n" + sm);

        // Deserializes contents of the SER file
        sm.readFromFile(serializedAdminData);
        System.out.println("test");
        System.out.println("Admins from ser:\n" + sm.toString());

        // adds a new student to StudentManager sm's records
        sm.add(new Admin("abenav", "abenav"));
        System.out.println("After:\n" + sm);

        // Writes the existing Student objects to file.
        // This data is serialized and written to file as a sequence of bytes.
        sm.saveToFile(serializedAdminData);
    }
     */
}
