package entities;

public class UnbanRequest extends Request {
    public UnbanRequest(String content, String username){
        super(content, new String[]{"Unban", "Ignore"}, username);
    }

    public String getUser(){ return super.getSender(); }

    @Override
    public String toString() {
        return String.format("%s\nThe User's username: \n%s\n%s", super.toString(), getUser(), optionsToString());
    }
}
