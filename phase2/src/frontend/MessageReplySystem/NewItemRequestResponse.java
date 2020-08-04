package frontend.MessageReplySystem;

import entities.Item;
import entities.NewItemRequest;
import presenters.MessageReplyPresenter;
import entities.Message;
import use_cases.GlobalInventoryManager;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.util.List;

public class NewItemRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private NewItemRequest message;
    private List<Message> messageList;
    private UserManager userManager;
    private GlobalInventoryManager globalInventoryManager;

    NewItemRequestResponse(NewItemRequest message, List<Message> messageList, UserManager userManager,
                           GlobalInventoryManager globalInventoryManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
        this.globalInventoryManager = globalInventoryManager;
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            messageList.remove(message);
            MessageBuilder messageBuilder = new MessageBuilder();

            Item item = message.getNewItem();
            globalInventoryManager.addItemToHashMap(item);
            //Informing the other user
            userManager.addUserMessage(item.getOwnerName(),
                    messageBuilder.getSystemMessage("Your Item: "+item+
                            "\nHas been successfully added to the system"));
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);
            MessageBuilder messageBuilder = new MessageBuilder();

            Item item = message.getNewItem();

            //Informing the other user
            userManager.addUserMessage(item.getOwnerName(),
                    messageBuilder.getSystemMessage("Your Item: "+item+
                            "\nHas been rejected"));
        }
    }
}
