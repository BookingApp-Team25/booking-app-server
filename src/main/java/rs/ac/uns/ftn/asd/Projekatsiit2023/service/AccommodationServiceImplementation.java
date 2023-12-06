package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class AccommodationServiceImplementation implements AccommodationService{
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Override
    public String createAccommodation(Accommodation accommodation){
        accommodationRepository.save(accommodation);
        return "Accommodation saved";
    }
    @Override
    public List<AccommodationSummaryResponse> getAllAccommodations(){
        ArrayList<Accommodation> fullList = new ArrayList<Accommodation>(accommodationRepository.findAll());
        ArrayList<AccommodationSummaryResponse> summary = new ArrayList<>();
        for (Accommodation accommodation : fullList){
            summary.add(new AccommodationSummaryResponse(accommodation.getId(),accommodation.getName(),
                    accommodation.getPhotos().get(0),accommodation.getDescription(),accommodation.getPrice(),
                    5));
        }
       return summary;
    }
    @Override
    public List<AccommodationSummaryResponse> getHostAccommodations(int hostId){
        return new ArrayList<>();
    }
    public AccommodationResponse getAccommodation(int accommodationId){
        return new AccommodationResponse();
    }

    @Override
    public Collection<AccommodationSummaryResponse> searchAccommodations(String city, DatePeriod datePeriod, int guestNumber) {
        return null;
    }

    @Override
    public Collection<AccommodationSummaryResponse> searchAccommodationsFiltered(String city,DatePeriod datePeriod,int guestNumber,AccommodationFilteredSearchRequest accommodationFilteredSearchRequest) {
        return null;
    }
    @Override
    public boolean addFavoriteAccommodation(int accommodationId) { return false; } //mozda treba menjati return tip
}
