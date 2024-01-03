package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class HostReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @OneToOne
    @JoinColumn(name="guest_id",referencedColumnName = "id")
    Guest guest;
    @ManyToOne
    @JoinColumn(name="host_id",referencedColumnName = "id")
    Host host;
    @Column(name="comment",updatable = false)
    String comment;
    @Column(name="rating",updatable = false)
    double rating;

    @Column(name="date",updatable = false)
    LocalDate date;

    public HostReview() {
    }

    public HostReview(String comment, double rating) {
        this.comment = comment;
        this.rating = rating;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Guest getGuest() {
        return guest;
    }

    public Host getHost() {
        return host;
    }

    public String getComment() {
        return comment;
    }

    public double getRating() {
        return rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
