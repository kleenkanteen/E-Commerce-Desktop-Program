package entities;

public class ReportRequest extends Request{
    private String contentReported;
    private String reportedPerson;
    public ReportRequest(String content, String sender, String contentReported, String reportedPerson){
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
        return String.format("Reporting a User\nReason of Reporting: \n%s\nThe Reported Content: " +
                "\n%s\nPerson being Reported: \n%s\n%s",
                getContent(),contentReported,reportedPerson,optionsToString());
    }
}
