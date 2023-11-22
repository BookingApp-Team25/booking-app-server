package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.Collection;

public interface HostService {

    public AccommodationResponse sendEditRequest(int id);
    public Collection<AccommodationLogDataResponse> getLogsForPeriod(DatePeriod period);
    public ReviewResponse reportReview(int reviewId);
}
