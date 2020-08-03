package frontend.MessageReplySystem;

public interface MessageResponse {
    //public abstract String getMessageString();
    public abstract String[] getActions();
    public abstract void doAction(String action);
}
