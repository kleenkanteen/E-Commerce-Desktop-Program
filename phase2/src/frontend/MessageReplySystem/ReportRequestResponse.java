package frontend.MessageReplySystem;

import entities.ReportRequest;
import presenters.MessageReplyPresenter;
import entities.Message;
import use_cases.UserManager;

import java.util.List;

public class ReportRequestResponse implements MessageResponse {
    private MessageReplyPresenter messageReplyPresenter = new MessageReplyPresenter();
    private ReportRequest message;
    private List<Message> messageList;
    private UserManager userManager;

    ReportRequestResponse(ReportRequest message, List<Message> messageList, UserManager userManager){
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
            userManager.banUserAccount(message.getReportedPerson());
        }
        else if(action.equals(validActions[1])){
            messageList.remove(message);
        }
        else if(action.equals(validActions[2])){
            messageList.remove(message);
            userManager.banUserAccount(message.getReporter());
        }
    }
}
