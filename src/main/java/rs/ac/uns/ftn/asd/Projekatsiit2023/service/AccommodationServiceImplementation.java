package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccommodationServiceImplementation implements AccommodationService{
    @Override
    public String createAccommodation(AccommodationRequest accommodationRequest) { // samo za sad vraca response direktno!
        return "nesto";
    }
    @Override
    public List<AccommodationSummaryResponse> getAllAccommodations(){
        return new ArrayList<>();
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
}
