package epam.pratsaunik.tickets.command;

public class CommandResult {
    public enum ResponseType{
        FORWARD, REDIRECT
    }

    public CommandResult() {
    }

    public CommandResult(ResponseType responseType, String responsePage) {

        this.responseType = responseType;
        this.responsePage = responsePage;
    }


    private ResponseType responseType;
    private String responsePage;

    public ResponseType getResponseType() {
        return responseType;
    }

    public String getResponsePage() {
        return responsePage;
    }

    public void setResponsePage(String responsePage) {
        this.responsePage = responsePage;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;

    }
}
