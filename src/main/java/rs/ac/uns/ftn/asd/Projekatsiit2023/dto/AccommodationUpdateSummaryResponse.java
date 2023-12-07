package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateType;

import java.util.UUID;

public class AccommodationUpdateSummaryResponse {
    private UUID id;
    private String accommodationName;
    private String accommodationDescritpion;
    private String accommodationPhoto;
    private AccommodationUpdateType type;

    public AccommodationUpdateSummaryResponse() {
    }

    public AccommodationUpdateSummaryResponse(UUID id, String accommodationName, String accommodationDescritpion, String accommodationPhoto, AccommodationUpdateType type) {
        this.id = id;
        this.accommodationName = accommodationName;
        this.accommodationDescritpion = accommodationDescritpion;
        this.accommodationPhoto = accommodationPhoto;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public String getAccommodationDescritpion() {
        return accommodationDescritpion;
    }

    public String getAccommodationPhoto() {
        return accommodationPhoto;
    }

    public AccommodationUpdateType getType() {
        return type;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public void setAccommodationDescritpion(String accommodationDescritpion) {
        this.accommodationDescritpion = accommodationDescritpion;
    }

    public void setAccommodationPhoto(String accommodationPhoto) {
        this.accommodationPhoto = accommodationPhoto;
    }

    public void setType(AccommodationUpdateType type) {
        this.type = type;
    }
}
