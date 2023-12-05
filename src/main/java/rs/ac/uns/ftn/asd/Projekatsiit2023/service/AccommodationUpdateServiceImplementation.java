package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationUpdate;

import java.util.Collection;

@Service
public class AccommodationUpdateServiceImplementation implements AccommodationUpdateService{
    @Override
    public Collection<AccommodationSummaryResponse> getAllAccommodationUpdates() {
        return null;
    }
    @Override
    public AccommodationUpdateResponse getAccommodationUpdate(int id){
        return null;
    }
    @Override
    public MessageResponse resolveAccommodationUpdate(int id, int flag) {
        return null;
    }

    @Override
    public MessageResponse createAccommodationUpdate(AccommodationUpdateRequest accommodationUpdateRequest) {
        return null;
    }

    @Override
    public MessageResponse createEditRequest(int id) {
        return null;
    }
}
