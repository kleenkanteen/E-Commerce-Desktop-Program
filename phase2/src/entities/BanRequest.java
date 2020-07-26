package entities;

public class BanRequest extends Request{
    private String contentReported;
    private String reportedPerson;
    public BanRequest(String content, String sender, String contentReported, String reportedPerson){
        super(content, new String[]{"Ban", "Ignore", "Ban Reporter"}, sender);
        this.contentReported = contentReported;
        this.reportedPerson = reportedPerson;
    }

    public String getReportedPerson(){
        return reportedPerson;
    }

    public String getContentReported(){
        return contentReported;
    }

    public String getReporter(){
        return super.getSender();
    }

    @Override
    public String toString() {
        return String.format("%s\nThe Reported Content: \n%s\nPerson Reported: \n%s\n%s"
                ,super.toString(),contentReported,reportedPerson,optionsToString());
    }
}
