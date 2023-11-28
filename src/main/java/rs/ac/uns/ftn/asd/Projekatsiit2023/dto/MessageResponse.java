package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

public class MessageResponse {
    private Boolean succesful;
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(Boolean succesful, String message) {
        this.succesful = succesful;
        this.message = message;
    }

    public Boolean getSuccesful() {
        return succesful;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccesful(Boolean succesful) {
        this.succesful = succesful;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
