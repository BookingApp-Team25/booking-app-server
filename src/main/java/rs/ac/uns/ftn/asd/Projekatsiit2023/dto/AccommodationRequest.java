package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;

import java.util.List;

public class AccommodationRequest {

    private String name;
    private String description;
    private Location location;
    private List<String> amenities;
    private List<String>  photos;
    private int minGuests;
    private int maxGuests;
    private AccommodationType type;
    private AccommodationReservedDates availability;
    private AccommodationPricelist pricelist;
    private double price;
    private int daysBefore;
    private AccommodationReservationPolicy policy;
    private double averageRating;
    private List<AccommodationReview> reviews;
//    private List<Reservation> reservations;

    public AccommodationRequest(String name, String description, Location location, List<String> amenities, List<String> photos, int minGuests, int maxGuests, AccommodationType type, AccommodationReservedDates availability, double price, AccommodationPricelist pricelist, int daysBefore, AccommodationReservationPolicy policy, double averageRating, List<AccommodationReview> reviews){//, List<Reservation> reservations) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.photos = photos;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.availability = new AccommodationReservedDates();
        this.price = price;
        this.pricelist = pricelist;
        this.daysBefore = daysBefore;
        this.policy = policy;
        this.averageRating = averageRating;
        this.reviews = reviews;
//        this.reservations = reservations;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public AccommodationType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public void setAvailability(AccommodationReservedDates availability) {
        this.availability = availability;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AccommodationReservedDates getAvailability() {
        return availability;
    }

    public AccommodationPricelist getPricelist() {
        return pricelist;
    }

    public void setPricelist(AccommodationPricelist pricelist) {
        this.pricelist = pricelist;
    }

    public int getDaysBefore() {
        return daysBefore;
    }

    public AccommodationReservationPolicy getPolicy() {
        return policy;
    }

    public void setDaysBefore(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    public void setPolicy(AccommodationReservationPolicy policy) {
        this.policy = policy;
    }

    public double getAverageRating() { return averageRating; }

    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public List<AccommodationReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<AccommodationReview> reviews) {
        this.reviews = reviews;
    }
//    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public void setReservations(List<Reservation> reservations) {
//        this.reservations = reservations;
//    }
}
