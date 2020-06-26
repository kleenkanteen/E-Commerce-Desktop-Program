import java.util.ArrayList;

public abstract class DecisionMessage extends Message{
    ArrayList<String> options;
    public DecisionMessage(String content, ArrayList<String> options){
        super(content);
        this.options = options;
    }
    public abstract void MakingDecision(String option);


}
