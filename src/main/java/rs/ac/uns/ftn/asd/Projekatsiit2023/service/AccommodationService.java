package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationFilteredSearchRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationSearchRequest;

import java.util.Collection;

public interface AccommodationService {
    public AccommodationResponse createAccommodation(AccommodationRequest accommodationRequest);
    public Collection<AccommodationResponse> getAllAccommodations();
    public AccommodationResponse getAccommodation(int accommodationId);
    public Collection<AccommodationResponse> getHostAccommodations(int hostId);

    public Collection<AccommodationResponse> searchAccommodations(AccommodationSearchRequest accommodationSearchRequest);

    public Collection<AccommodationResponse> searchAccommodationsFiltered(AccommodationFilteredSearchRequest accommodationFilteredSearchRequest);

}
