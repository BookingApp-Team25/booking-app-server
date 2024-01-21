package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class AccommodationReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    UUID id;
    @ManyToOne
    @JoinColumn(name="guest_id",referencedColumnName = "id")
    Guest guest;

    @ManyToOne
    @JoinColumn(name="accommodation_id",referencedColumnName = "id")
    Accommodation accommodation;

    @Column(name="date",updatable = false)
    LocalDate date;

    @Column(name="comment",updatable = false)
    String comment;
    @Column(name="rating",updatable = false)
    double rating;

    @Column(name="reported",updatable = true)
    Boolean reported;
    public AccommodationReview() {
    }

    public AccommodationReview(String comment, double rating) {
        this.comment = comment;
        this.rating = rating;
        this.id = UUID.randomUUID();
        this.reported=false;
        this.date=LocalDate.now();
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
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

    public Accommodation getAccommodation() {
        return accommodation;
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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Boolean isReported() {
        return reported;
    }

    public void setReported(Boolean reported) {
        this.reported = reported;
    }
}