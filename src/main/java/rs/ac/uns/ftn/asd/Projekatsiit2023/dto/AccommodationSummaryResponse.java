package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import java.util.UUID;

public class AccommodationSummaryResponse {
    UUID accommodationId;
    String name;
    String photo;
    String description;
    double price;
    double rating;

    public AccommodationSummaryResponse() {
    }

    public AccommodationSummaryResponse(UUID accommodationId, String name, String photo, String description, double price, double rating) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public UUID getAccommodationId() {
        return accommodationId;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public void setAccommodationId(UUID accommodationId) {
        this.accommodationId = accommodationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
