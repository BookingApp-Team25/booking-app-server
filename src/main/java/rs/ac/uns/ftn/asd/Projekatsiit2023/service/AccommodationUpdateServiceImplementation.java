package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationSummaryResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationUpdateResponse;
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
}
