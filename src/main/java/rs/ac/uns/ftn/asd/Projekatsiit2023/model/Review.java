package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.util.UUID;

@Entity
public class Review {
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
    @Column(name="comment",updatable = false)
    String comment;
    @Column(name="rating",updatable = false)
    double rating;
    public Review() {
    }

    public Review(String comment, double rating) {
        this.comment = comment;
        this.rating = rating;
        this.id = UUID.randomUUID();
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

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
