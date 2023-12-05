package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;

import java.util.Collection;

public interface AccommodationUpdateService {
    public Collection<AccommodationSummaryResponse> getAllAccommodationUpdates();
    public AccommodationUpdateResponse getAccommodationUpdate(int id);
    public MessageResponse resolveAccommodationUpdate(int id, int flag);

    MessageResponse createAccommodationUpdate(AccommodationUpdateRequest accommodationUpdateRequest);

    MessageResponse createEditRequest(int id);
}
