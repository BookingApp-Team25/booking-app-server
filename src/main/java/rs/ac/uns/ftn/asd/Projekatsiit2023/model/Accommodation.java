package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationOnHoldStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationType;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.PriceCalculationMethod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AccommodationUpdate> updates;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "amenities", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "amenity", nullable = false)
    private List<String> amenities;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "accommodation_id")
    public List<Image>  photos;
    @Column(name = "minGuests", nullable = false)
    private int minGuests;
    @Column(name = "maxGuests", nullable = false)
    private int maxGuests;
    @Enumerated(EnumType.STRING)
    private AccommodationType type;
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<AccommodationDatePeriod> availability;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pricelist_id", referencedColumnName = "id")
    private AccommodationPricelist pricelist;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "rating", nullable = false)
    private double rating;
    @Column(name = "daysBefore", nullable = false)
    private int daysBefore;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL,fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AccommodationReview> reviews;

    @Enumerated(EnumType.STRING)
    private AccommodationReservationPolicy policy;
    @Enumerated(EnumType.STRING)
    private AccommodationOnHoldStatus onHoldStatus;

    @Enumerated(EnumType.STRING)
    private PriceCalculationMethod priceCalculationMethod;
    private List<AccommodationDatePeriod> createAccommodationDatePeriods(List<AccommodationDatePeriod> datePeriods){
        List<AccommodationDatePeriod> periods = new ArrayList<AccommodationDatePeriod>();
        for (AccommodationDatePeriod datePeriod : datePeriods){
            periods.add(new AccommodationDatePeriod(datePeriod.getStartDate(),datePeriod.getEndDate(),this, datePeriod.isAppliedWeekend(),datePeriod.isAppliedSummer(),datePeriod.isAppliedHoliday(),datePeriod.isAppliedWinter()));
        }
        return periods;
    }
    public List<DatePeriod> getAvailabilityDatePeriods(){
        List<DatePeriod> periods = new ArrayList<DatePeriod>();
        for (AccommodationDatePeriod datePeriod : this.availability){
            periods.add(new DatePeriod(datePeriod.getStartDate(),datePeriod.getEndDate()));
        }
        return periods;
    }

    public Accommodation() {
    }

    public Accommodation(String name, String description, Location location, List<String> amenities, List<Image> photos, int minGuests, int maxGuests, AccommodationType type, double price, AccommodationPricelist pricelist, int daysBefore, AccommodationReservationPolicy policy) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.photos = photos;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.availability = new ArrayList<AccommodationDatePeriod>();
        this.price = price; //ne koristiti/ukloniti
        this.pricelist = pricelist;
        this.daysBefore = daysBefore;
        this.policy = policy;
        this.reviews=new ArrayList<AccommodationReview>();
        this.rating=0;
    }
    public List<String> getPhotosEncoded() throws IOException {
        List<String> encodedPhotos = new ArrayList<String>();
        for(Image image : this.photos){
           String imagePath = image.getImagePath();
           byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
           String base64Image = Base64.getEncoder().encodeToString(imageBytes);
           String fullImage = "data:image/png;base64," + base64Image;
           encodedPhotos.add(fullImage);
        }
        return encodedPhotos;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public AccommodationOnHoldStatus getOnHoldStatus() {
        return onHoldStatus;
    }

    public void setOnHoldStatus(AccommodationOnHoldStatus onHoldStatus) {
        this.onHoldStatus = onHoldStatus;
    }

    public double getRating() {
        return rating;
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

    public List<Image> getPhotos() {
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

    public void setPhotos(List<Image> photos) {
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

    public void setAvailability(List<AccommodationDatePeriod> availability) {
        this.availability = availability;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<AccommodationDatePeriod> getAvailability() {
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
    public void processAccommodationRequest(AccommodationRequest accommodationRequest) throws IOException {
        this.name = accommodationRequest.getName();
        this.description = accommodationRequest.getDescription();
        this.location = accommodationRequest.getLocation();
        this.amenities = accommodationRequest.getAmenities();
        //this.photos = accommodationRequest.getPhotos();
        this.minGuests = accommodationRequest.getMinGuests();
        this.maxGuests = accommodationRequest.getMaxGuests();
        this.type = accommodationRequest.getType();
        List<AccommodationDatePeriod> newDatePeriods = createAccommodationDatePeriods(accommodationRequest.getAvailability());
        if(this.availability == null){
            this.availability = new ArrayList<>();
        }
        this.availability.clear();
        this.availability.addAll(newDatePeriods);
        this.price = accommodationRequest.getPrice();
        this.pricelist = accommodationRequest.getPricelist();
        this.daysBefore = accommodationRequest.getDaysBefore();
        this.policy = accommodationRequest.getPolicy();
        this.rating=0;
        this.priceCalculationMethod = accommodationRequest.getPriceCalculationMethod();
    }

    public PriceCalculationMethod getPriceCalculationMethod() {
        return priceCalculationMethod;
    }

    public void setPriceCalculationMethod(PriceCalculationMethod priceCalculationMethod) {
        this.priceCalculationMethod = priceCalculationMethod;
    }

    public List<AccommodationReview> getReviews() {
        return this.reviews;
    }

    public void addReview(AccommodationReview review){
        this.reviews.add(review);
    }
    public void removeReview(AccommodationReview review){
        this.reviews.remove(review);
    }
    public void updateRating(){
        int n=0;
        double rating=0;
        if(reviews.isEmpty()){
            this.rating=0;
        }else {
            for (AccommodationReview review : reviews) {
                rating = rating + review.getRating();
                n++;
            }
            this.rating = rating / n;
        }
    }
}