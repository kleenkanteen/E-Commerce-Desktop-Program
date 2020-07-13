package C_controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import D_presenters.MessageReplyMenu;
import F_entities.*;
import E_use_cases.*;

public class AdminMessageReplySystem {
    private AdminManager am;
    private GlobalInventoryManager gi;
    private UserManager um;
    private String accountUsername;
    private MessageReplyMenu mm;

    /**
     * Class constructor.
     * Create a new UserMessageReplySystem that controls and allows the admin to reply to system messages
     * @param accountUsername the username of the currently logged in User
     * @param um the user manager of the system
     * @param am the admin manager of the system
     * @param gi the global inventory manager of the system
     */
    public AdminMessageReplySystem(AdminManager am, GlobalInventoryManager gi, UserManager um, String accountUsername){
        this.am = am;
        this.gi = gi;
        this.um = um;
        this.accountUsername = accountUsername;
        mm = new MessageReplyMenu();
    }

    /**
     * Interacts with the admin to allow them to respond and reply to system messages
     */
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Message> messages = am.getAdminMessagesArrayList();
        if(messages.size() == 0){
            mm.printNoMessages();
            mm.printExit();
            return;
        }
        try {
            String input = "";
            while(true) {
                mm.printMenuPrompt(messages.size());
                input = br.readLine();
                if(input.equals("2"))return;
                else if(input.equals("1")) break;
                else mm.printInvalidInput();
            }
            final ArrayList<Message> loopingMessages =  new ArrayList<Message>(messages);
            for(Message m: loopingMessages){
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
        }catch(IOException e){
            mm.printErrorOccurred();
        }finally {
            am.setAdminMessagesArrayList(messages);
            mm.printExit();
        }
    }
    private boolean ContentMessageResponse(Message m, ArrayList<Message> messages,
                                           BufferedReader br) throws IOException {
        while (true) {
            mm.printContentMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "1":
                    messages.remove(m);
                    return true;
                case "2":
                    return true;
                case "3":
                    return false;
                default:
                    mm.printInvalidInput();
            }
        }
    }
    private boolean UnfreezeRequestMessageResponse(UnfreezeRequestMessage m, ArrayList<Message> messages,
                                                   BufferedReader br) throws IOException{
        String u = m.getUser();
        while (true) {
            mm.printDecisionMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    return true;
                case "b":
                    return false;
                case "1":
                    um.freezeUserAccount(u, false);
                    messages.remove(m);
                    createMessage(u, "Your account is unfrozen by the Admin "+accountUsername);
                    mm.success();
                    return true;
                case "2":
                    messages.remove(m);
                    createMessage(u, "Your request is rejected by the Admin "+accountUsername);
                    mm.success();
                    return true;
                 default:
                     mm.printInvalidInput();
            }
        }
    }
    private boolean FreezeRequestMessageResponse(FreezeRequestMessage m, ArrayList<Message> messages,
                                                 BufferedReader br) throws IOException{
        String u = m.getUser();
        while (true) {
            mm.printDecisionMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    return true;
                case "b":
                    return false;
                case "1":
                    um.freezeUserAccount(u, true);
                    messages.remove(m);
                    createMessage(u, "Your account is frozen by the Admin "+accountUsername);
                    mm.success();
                    return true;
                case "2":
                    messages.remove(m);
                    mm.success();
                    return true;
                default:
                    mm.printInvalidInput();
            }
        }
    }

    private boolean NewItemMessageResponse(NewItemMessage m, ArrayList<Message> messages,
                                           BufferedReader br) throws IOException{
        Item item = m.getNewItem();
        while (true) {
            mm.printDecisionMessagePrompt(m);
            String input = br.readLine();
            switch (input){
                case "a":
                    return true;
                case "b":
                    return false;
                case "1":
                    gi.addItemToHashMap(item);
                    um.addItemToUserInventory(item, item.getOwnerName());
                    messages.remove(m);
                    createMessage(item.getOwnerName(), "Your Item: "+item+
                            "\n has been successfully added to the system");
                    mm.success();
                    return true;
                case "2":
                    messages.remove(m);
                    createMessage(item.getOwnerName(), "Your Item: "+item+
                            "\n has been rejected by the Admin "+accountUsername);
                    mm.success();
                    return true;
                default:
                    mm.printInvalidInput();
            }
        }
    }
    private void createMessage(String username, String content){
        Message reply = new Message(content);
        ArrayList<Message> temp = um.getUserMessages(username);
        temp.add(reply);
        um.setUserMessages(username, temp);
    }
}
