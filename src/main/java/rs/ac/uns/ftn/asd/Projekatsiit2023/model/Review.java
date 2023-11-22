package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import java.util.UUID;

public class Review {
    UUID id;
    String comment;
    double rating;

    public Review() {
    }

    public Review(String comment, double rating) {
        this.comment = comment;
        this.rating = rating;
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
