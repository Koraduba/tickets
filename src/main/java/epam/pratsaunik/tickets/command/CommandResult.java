package epam.pratsaunik.tickets.command;

/**
 * return instance of command with type of response and page included
 */
public class CommandResult {
    public enum ResponseType{
        FORWARD, REDIRECT
    }
    private ResponseType responseType;
    private String responsePage;

    public CommandResult() {
    }

    public CommandResult(ResponseType responseType, String responsePage) {

        this.responseType = responseType;
        this.responsePage = responsePage;
    }




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
