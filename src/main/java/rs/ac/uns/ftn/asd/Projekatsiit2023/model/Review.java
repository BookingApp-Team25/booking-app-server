package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.util.UUID;

public class Review {
    UUID id;
    UUID guestId;
    UUID reviewedEntity;
    String comment;
    double rating;
    ReviewType type;
    public Review() {
    }

    public Review(String comment, double rating, UUID guestId, UUID reviewedEntity, ReviewType type) {
        this.comment = comment;
        this.rating = rating;
        this.guestId = guestId;
        this.reviewedEntity = reviewedEntity;
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
