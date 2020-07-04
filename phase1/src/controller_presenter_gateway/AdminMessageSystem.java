package controller_presenter_gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import entities.*;
import uses_cases.*;

public class AdminMessageSystem {
    private ArrayList<Message> messages;
    private GlobalInventoryManager gi;
    private UserManager um;
    private Admin account;
    private AdminMessageMenu amm;

    public AdminMessageSystem(ArrayList<Message> messages, GlobalInventoryManager gi, UserManager um,
                              Admin account){
        this.messages = messages;
        this.gi = gi;
        this.um = um;
        this.account = account;
        amm = new AdminMessageMenu(messages);
    }

    public void run() {
        //TODO split the controller and presenter
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        amm.printMenu();
        try {
            String input = null;
            while(true) {
                input = br.readLine();
                if(input.equals("exit"))return;
                if(input.equals("view"))break;
            }

            for(int i = 0; i < messages.size(); i++){
                Message m = messages.get(i);
                amm.printMessage(i);
                if(m instanceof NewItemMessage){
                    if(!NewItemMessageResponse((NewItemMessage) m, br))return;
                }
                else if(m instanceof FreezeRequestMessage){
                    if(!FreezeRequestMessageResponse((FreezeRequestMessage) m, br))return;
                }
                else if(m instanceof UnfreezeRequestMessage){
                    if(!UnfreezeRequestMessageResponse((UnfreezeRequestMessage) m, br))return;
                }
                else {
                    if (!ContentMessageResponse(m, br)) return;
                }
            }
            br.close();
        }catch(IOException e){
            System.out.println("Something went wrong");
        }
    }
    private boolean ContentMessageResponse(Message m, BufferedReader br) throws IOException {
        amm.printContentMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit")) return false;
            if(input.equals("delete")) {
                messages.remove(m);
                return true;
            }
            if(input.equals("next"))return true;
        }
    }
    private boolean UnfreezeRequestMessageResponse(UnfreezeRequestMessage m, BufferedReader br) throws IOException{
        amm.printDecisionMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit")) return false;
            if(input.equals("next")) break;
            if(input.equals("1")){
                User u = m.getUser();
                um.freezeUserAccount(u.getUsername(), false);
                messages.remove(m);

                Message reply = new Message("Your account is unfrozen by the Admin "+account.getUsername());
                ArrayList<Message> temp = um.getUserMessages(u.getUsername());
                temp.add(reply);
                um.setUserMessages(u.getUsername(), temp);
                break;
            }
            else if(input.equals("2")){
                User u = m.getUser();
                messages.remove(m);

                Message reply = new Message("Your request is rejected by the Admin "+account.getUsername());
                ArrayList<Message> temp = um.getUserMessages(u.getUsername());
                temp.add(reply);
                um.setUserMessages(u.getUsername(), temp);
                break;
            }
        }
        return true;
    }
    private boolean FreezeRequestMessageResponse(FreezeRequestMessage m, BufferedReader br) throws IOException{
        amm.printDecisionMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit"))return false;
            if(input.equals("next")){
                break;
            }
            if(input.equals("1")){
                User u = m.getUser();
                um.freezeUserAccount(u.getUsername(), true);
                messages.remove(m);

                Message reply = new Message("Your account is frozen by the Admin "+account.getUsername());
                ArrayList<Message> temp = um.getUserMessages(u.getUsername());
                temp.add(reply);
                um.setUserMessages(u.getUsername(), temp);
                break;
            }
            else if(input.equals("2")){
                messages.remove(m);
                break;
            }
        }
        return true;
    }

    private boolean NewItemMessageResponse(NewItemMessage m, BufferedReader br) throws IOException{
        amm.printDecisionMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit"))return false;
            if(input.equals("next")){
                    break;
            }
            if(input.equals("1")){
                Item item = ((NewItemMessage) m).getNewItem();
                gi.addItemToHashMap(item);
                um.addItemToUserInventory(item, item.getOwnerName());
                messages.remove(m);

                Message reply = new Message("Your Item: "+item+
                            "\n has been successfully added to the system");
                ArrayList<Message> temp = um.getUserMessages(item.getOwnerName());
                temp.add(reply);
                um.setUserMessages(item.getOwnerName(), temp);
                break;
            }
            else if(input.equals("2")){
                messages.remove(m);

                Item item = ((NewItemMessage) m).getNewItem();
                Message reply = new Message("Your Item: "+item+
                            "\n has been rejected by the Admin "+account.getUsername());
                ArrayList<Message> temp = um.getUserMessages(item.getOwnerName());
                temp.add(reply);
                um.setUserMessages(item.getOwnerName(), temp);
                break;
            }
        }
        return true;
    }
}
