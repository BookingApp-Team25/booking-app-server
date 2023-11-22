package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponse {
    UUID reciever;
    UUID sender;
    String information;
    LocalDateTime sendTime;

    public NotificationResponse() {
    }

    public NotificationResponse(UUID reciever, UUID sender, String information, LocalDateTime sendTime) {
        this.reciever = reciever;
        this.sender = sender;
        this.information = information;
        this.sendTime = sendTime;
    }

    public UUID getReciever() {
        return reciever;
    }

    public UUID getSender() {
        return sender;
    }

    public String getInformation() {
        return information;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setReciever(UUID reciever) {
        this.reciever = reciever;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
