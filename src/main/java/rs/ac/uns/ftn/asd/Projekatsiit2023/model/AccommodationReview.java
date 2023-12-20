package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.util.UUID;

@Entity
public class AccommodationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    UUID id;
    UUID guestId;
    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
    String comment;
    double rating;
    ReviewType type;
    public AccommodationReview() {
    }

    public AccommodationReview(String comment, double rating, UUID guestId, Accommodation accommodation, ReviewType type) {
        this.comment = comment;
        this.rating = rating;
        this.guestId = guestId;
        this.accommodation = accommodation;
        this.id = UUID.randomUUID();
        this.type = type;
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
}