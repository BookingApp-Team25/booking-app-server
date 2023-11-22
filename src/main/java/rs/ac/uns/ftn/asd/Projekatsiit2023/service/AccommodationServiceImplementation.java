package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AccommodationServiceImplementation implements AccommodationService{
    @Override
    public AccommodationResponse createAccommodation(AccommodationRequest accommodationRequest) { // samo za sad vraca response direktno!
        return new AccommodationResponse(accommodationRequest.getName(),
                accommodationRequest.getDescription(),
                accommodationRequest.getLocation(),
                accommodationRequest.getAmenities(),
                accommodationRequest.getPhotos(),
                accommodationRequest.getMinGuests(),
                accommodationRequest.getMaxGuests(),
                accommodationRequest.getType(),
                accommodationRequest.getAvailability(),
                accommodationRequest.getPrice(),
                accommodationRequest.getPricelist(),
                accommodationRequest.getDaysBefore(),
                accommodationRequest.getPolicy());
    }
    @Override
    public List<AccommodationResponse> getAllAccommodations(){
        return new ArrayList<>();
    }
    @Override
    public List<AccommodationResponse> getHostAccommodations(int hostId){
        return new ArrayList<>();
    }
    public AccommodationResponse getAccommodation(int accommodationId){
        return new AccommodationResponse();
    }

}
