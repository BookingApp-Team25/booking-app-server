package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.util.UUID;

public class ReviewRequest {
    String guestUsername;
    UUID reviewedEntity;
    String comment;
    double rating;
    ReviewType type;

    public ReviewRequest() {
    }

    public ReviewRequest(String comment, double rating, String guestId, UUID reviewedEntity, ReviewType type) {
        this.comment = comment;
        this.rating = rating;
        this.guestUsername = guestUsername;
        this.reviewedEntity = reviewedEntity;
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

    public String getGuestUsername() {
        return guestUsername;
    }

    public UUID getReviewedEntity() {
        return reviewedEntity;
    }

    public ReviewType getType() {
        return type;
    }
}
