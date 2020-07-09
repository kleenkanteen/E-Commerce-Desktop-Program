package controller_presenter_gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import entities.*;
import uses_cases.*;

public class AdminMessageReplySystem {
    private AdminManager am;
    private GlobalInventoryManager gi;
    private UserManager um;
    private String accountUsername;
    private MessageReplyMenu mm;

    public AdminMessageReplySystem(AdminManager am, GlobalInventoryManager gi, UserManager um, String accountUsername){
        this.am = am;
        this.gi = gi;
        this.um = um;
        this.accountUsername = accountUsername;
        mm = new MessageReplyMenu(am.getAdminMessagesArrayList());
    }

    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Message> messages = am.getAdminMessagesArrayList();
        mm.printMenu();
        try {
            String input = null;
            while(true) {
                input = br.readLine();
                if(input.equals("exit"))return;
                if(input.equals("view"))break;
            }

            for(int i = 0; i < messages.size(); i++){
                Message m = messages.get(i);
                mm.printMessage(i);
                if(m instanceof NewItemMessage){
                    if(!NewItemMessageResponse((NewItemMessage) m, messages, br))return;
                }
                else if(m instanceof FreezeRequestMessage){
                    if(!FreezeRequestMessageResponse((FreezeRequestMessage) m, messages, br))return;
                }
                else if(m instanceof UnfreezeRequestMessage){
                    if(!UnfreezeRequestMessageResponse((UnfreezeRequestMessage) m, messages, br))return;
                }
                else {
                    if (!ContentMessageResponse(m, messages, br)) return;
                }
            }
            br.close();
        }catch(IOException e){
            System.out.println("Something went wrong");
        }
        am.setAdminMessagesArrayList(messages);
    }
    private boolean ContentMessageResponse(Message m, ArrayList<Message> messages,
                                           BufferedReader br) throws IOException {
        mm.printContentMessagePrompt();
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
    private boolean UnfreezeRequestMessageResponse(UnfreezeRequestMessage m, ArrayList<Message> messages,
                                                   BufferedReader br) throws IOException{
        mm.printDecisionMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit")) return false;
            if(input.equals("next")) break;
            if(input.equals("1")){
                String u = m.getUser();
                um.freezeUserAccount(u, false);
                messages.remove(m);

                Message reply = new Message("Your account is unfrozen by the Admin "+accountUsername);
                ArrayList<Message> temp = um.getUserMessages(u);
                temp.add(reply);
                um.setUserMessages(u, temp);
                break;
            }
            else if(input.equals("2")){
                String u = m.getUser();
                messages.remove(m);

                Message reply = new Message("Your request is rejected by the Admin "+accountUsername);
                ArrayList<Message> temp = um.getUserMessages(u);
                temp.add(reply);
                um.setUserMessages(u, temp);
                break;
            }
        }
        return true;
    }
    private boolean FreezeRequestMessageResponse(FreezeRequestMessage m, ArrayList<Message> messages,
                                                 BufferedReader br) throws IOException{
        mm.printDecisionMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit"))return false;
            if(input.equals("next")){
                break;
            }
            if(input.equals("1")){
                String u = m.getUser();
                um.freezeUserAccount(u, true);
                messages.remove(m);

                Message reply = new Message("Your account is frozen by the Admin "+accountUsername);
                ArrayList<Message> temp = um.getUserMessages(u);
                temp.add(reply);
                um.setUserMessages(u, temp);
                break;
            }
            else if(input.equals("2")){
                messages.remove(m);
                break;
            }
        }
        return true;
    }

    private boolean NewItemMessageResponse(NewItemMessage m, ArrayList<Message> messages,
                                           BufferedReader br) throws IOException{
        mm.printDecisionMessagePrompt();
        while (true) {
            String input = br.readLine();
            if(input.equals("exit"))return false;
            if(input.equals("next")){
                    break;
            }
            if(input.equals("1")){
                Item item = m.getNewItem();
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

                Item item =  m.getNewItem();
                Message reply = new Message("Your Item: "+item+
                            "\n has been rejected by the Admin "+accountUsername);
                ArrayList<Message> temp = um.getUserMessages(item.getOwnerName());
                temp.add(reply);
                um.setUserMessages(item.getOwnerName(), temp);
                break;
            }
        }
        return true;
    }
}
