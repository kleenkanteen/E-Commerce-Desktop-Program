import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import entities.*;
import uses_cases.*;

public class AdminMessageSystem {
    ArrayList<Message> messages;
    GlobalInventoryManager gi;
    UserManager um;

    public AdminMessageSystem(ArrayList<Message> messages, GlobalInventoryManager gi, UserManager um){
        this.messages = messages;
        this.gi = gi;
        this.um = um;
    }

    public void run() {
        //TODO split the controller and presenter
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("You have "+messages.size()+" messages");
        System.out.println("Type 'exit' to quit or 'view' to look through the messages.");
        try {
            String input = null;
            while(true) {
                input = br.readLine();
                if(input.equals("exit") || input.equals("view"))break;
            }
            if(input.equals("exit"))return;

            for(int i = 0; i < messages.size(); i++){
                Message m = messages.get(i);
                System.out.println("Message "+Integer.toString(i+1)+": "+m);
                //TODO think about spliting the if statements to methods
                if(m instanceof NewItemMessage){
                    String[] options = ((NewItemMessage) m).getOptions();
                    System.out.println("Enter an option number or 'next' to skip this message " +
                            "and view the next message or 'exit' to exit");
                    while (true) {
                        input = br.readLine();
                        if(input.equals("exit"))return;
                        if(input.equals("next")){
                            break;
                        }
                        if(input.equals("1")){
                            //TODO Add item to GI and assign itemID
                            //TODO Maybe add a function that sents a message to the User
                            Item item = ((NewItemMessage) m).getNewItem();
                            messages.remove(m);
                            break;
                        }
                        else if(input.equals("2")){
                            //TODO Maybe add a function that sents a message to the User
                            messages.remove(m);
                            break;
                        }
                    }
                }
                else if(m instanceof FreezeRequestMessage){
                    String[] options = ((FreezeRequestMessage) m).getOptions();
                    System.out.println("Enter an option number or 'next' to skip this message " +
                            "and view the next message or 'exit' to exit");
                    while (true) {
                        input = br.readLine();
                        if(input.equals("exit"))return;
                        if(input.equals("next")){
                            break;
                        }
                        if(input.equals("1")){
                            //TODO Maybe add a function that sents a message to the User
                            User u = ((FreezeRequestMessage) m).getUser();
                            um.freezeUserAccount(u.getUsername());
                            messages.remove(m);
                            break;
                        }
                        else if(input.equals("2")){
                            //TODO Maybe add a function that sents a message to the User
                            messages.remove(m);
                            break;
                        }
                    }
                }
                else {
                    System.out.println("Enter 'delete' to delete this message or 'next' to skip this " +
                            "message and view the next message or 'exit' to exit");
                    while (true) {
                        input = br.readLine();
                        if (input.equals("exit") || input.equals("delete") || input.equals("next")) break;
                    }
                    if(input.equals("exit"))return;
                    if(input.equals("delete"))messages.remove(m);
                }
            }
        }catch(IOException e){

        }


    }
}
