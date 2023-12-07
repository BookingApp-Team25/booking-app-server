package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;

import java.util.Collection;
import java.util.UUID;

public interface AccommodationUpdateService {
    public Collection<AccommodationUpdateSummaryResponse> getAllAccommodationUpdates();
    public AccommodationUpdateResponse getAccommodationUpdate(UUID id);
    public MessageResponse resolveAccommodationUpdate(UUID updateId, int flag);

    MessageResponse createAccommodationUpdate(AccommodationRequest accommodationRequest);

    MessageResponse createEditRequest(UUID id,AccommodationRequest accommodationRequest);
}
