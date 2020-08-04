package frontend.MessageReplySystem;

import entities.UnbanRequest;
import presenters.MessageReplyPresenter;
import entities.Message;
import use_cases.UserManager;

import java.util.List;

public class UnbanRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private UnbanRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    UnbanRequestResponse(UnbanRequest message, List<Message> messageList, UserManager userManager){
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
            messageList.remove(message);
            if(userManager.getUserIsBanned(message.getUser())){
                userManager.unFreezeUserAccount(message.getUser());
            }
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);
        }
    }
}
