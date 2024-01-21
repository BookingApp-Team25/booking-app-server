package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface AccommodationUpdateService {
    public Collection<AccommodationUpdateSummaryResponse> getAllAccommodationUpdates() throws IOException;
    public AccommodationUpdateResponse getAccommodationUpdate(UUID id);
    public MessageResponse resolveAccommodationUpdate(UUID updateId, int flag);
    public void insertPhotos(Accommodation accommodation, List<String> photos) throws IOException;

    public MessageResponse createAccommodationUpdate(AccommodationRequest accommodationRequest) throws IOException;

    public MessageResponse createEditRequest(UUID id,AccommodationRequest accommodationRequest) throws IOException;
}
