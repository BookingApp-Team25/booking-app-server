package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.util.UUID;

public class ReviewResponse {
    UUID id;
    String guestName;
    String reviewedEntityName;
    String comment;
    double rating;
    ReviewType type;

    public ReviewResponse() {
    }

    public ReviewResponse(String comment, double rating, String guestName, String reviewedEntityName, ReviewType type) {
        this.comment = comment;
        this.rating = rating;
        this.guestName = guestName;
        this.reviewedEntityName = reviewedEntityName;
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
