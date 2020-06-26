public abstract class Message {
    String content;
    public Message(String content){
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
