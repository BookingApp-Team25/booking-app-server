package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReviewType;

import java.time.LocalDate;
import java.util.UUID;

public class ReviewResponse {
    UUID id;
    String guestName;
    String reviewedEntityName;
    String comment;
    double rating;
    ReviewType type;
    String date;
    String guestUsername;
    boolean reported;
    public ReviewResponse() {
    }

    public ReviewResponse(UUID id,String comment, double rating, String guestName,
                          String reviewedEntityName, ReviewType type,LocalDate date,String guestUsername,boolean reported) {
        this.comment = comment;
        this.rating = rating;
        this.guestName = guestName;
        this.reviewedEntityName = reviewedEntityName;
        this.id = id;
        this.type = type;
        this.date=date.toString();
        this.guestUsername=guestUsername;
        this.reported=reported;
    }

    public String getGuestUsername() {
        return guestUsername;
    }

    public boolean isReported() {
        return reported;
    }

    public UUID getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getReviewedEntityName() {
        return reviewedEntityName;
    }

    public ReviewType getType() {
        return type;
    }

    public String getDate() {
        return date;
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
