package rs.ac.uns.ftn.asd.Projekatsiit2023.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User receiver;
    @Column(name = "information",nullable = false)
    String information;
    @Column(name = "send_time",nullable = false)
    LocalDateTime sendTime;

    @Column(name = "seen",nullable = false)
    Boolean seen;

    public Notification() {
    }

    public Notification(User receiver, UUID sender, String information, LocalDateTime sendTime, boolean seen) {
        this.receiver = receiver;
        this.information = information;
        this.sendTime = sendTime;
        this.seen = false;
    }
    public UUID getId() {
        return id;
    }
    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
    public User getReceiver() {
        return receiver;
    }


    public String getInformation() {
        return information;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
