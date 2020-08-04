package frontend.MessageReplySystem;

import entities.FreezeRequest;
import presenters.MessageReplyPresenter;
import entities.Message;
import use_cases.MessageBuilder;
import use_cases.UserManager;

import java.util.List;

public class FreezeRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private FreezeRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    FreezeRequestResponse(FreezeRequest message, List<Message> messageList, UserManager userManager){
        this.message = message;
        this.messageList = messageList;
        this.userManager = userManager;
    }
    @Override
    public String[] getActions() {
        return messageReplyPresenter.requestAction(message);
    }

    @Override
    public void doAction(String action) {
        String[]validActions = getActions();
        if(action.equals(validActions[0])){
            String username = message.getUser();
            MessageBuilder messageBuilder = new MessageBuilder();
            userManager.freezeUserAccount(username);
            messageList.remove(message);
            //informing the other user
            userManager.addUserMessage(username,
                    messageBuilder.getSystemMessage("Your account is frozen"));
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);
        }
    }
}
