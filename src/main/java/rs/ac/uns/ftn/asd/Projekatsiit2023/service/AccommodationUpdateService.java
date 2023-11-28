package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationSummaryResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationUpdateResponse;

import java.util.Collection;

public interface AccommodationUpdateService {
    public Collection<AccommodationSummaryResponse> getAllAccommodationUpdates();
    public AccommodationUpdateResponse getAccommodationUpdate(int id);
}
