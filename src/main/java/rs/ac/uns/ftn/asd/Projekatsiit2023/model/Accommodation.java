package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;

import java.util.List;
import java.util.UUID;

public class Accommodation {

    private UUID id;
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

    public Accommodation(String name, String description, Location location, List<String> amenities, List<String> photos, int minGuests, int maxGuests, AccommodationType type, AccommodationReservedDates availability, double price, AccommodationPricelist pricelist, int daysBefore, AccommodationReservationPolicy policy) {
        this.id = UUID.randomUUID();
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

    public void setDaysBefore(int daysBefore) {
        this.daysBefore = daysBefore;
    }

    public UUID getId() {
        return id;
    }

    public AccommodationReservationPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(AccommodationReservationPolicy policy) {
        this.policy = policy;
    }
}
