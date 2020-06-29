package com.jetbrains;


import java.io.IOException;

public class MainMenu {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        demoAdminManager();
    }

    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void demoAdminManager() throws IOException, ClassNotFoundException {

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

        //adds a new admin to AdminManager am's records
        //am.add(new Admin("very cool ", "dude123"));
        am.add(new Admin("bonehead", "chickennugget"));
        am.add(new Admin("very cool ", "dude123"));

        //am.login("Macleord", "superpass");

        //adds a new student to AdminManager am2's records
        //am2.add(new Message("ok moo"));
        //am2.add(new Message("cow head"));
        //am2.remove(new Message("ok moo"));
        //am2.remove(new Message("great work son"));


        System.out.println("Admin Accounts after:");
        System.out.println(am);
        System.out.println("Admin Messages after");
        System.out.println(am2);

        // Writes the existing Admin objects to file.
        // This data is serialized and written to file as a sequence of bytes.
        am.saveToFile(serializedAdminManagerAccountInfo);
        am2.saveToFile(serializedAdminManagerMessageInfo);
    }
}
