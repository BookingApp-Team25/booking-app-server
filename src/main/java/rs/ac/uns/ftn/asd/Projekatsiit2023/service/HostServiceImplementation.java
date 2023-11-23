package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class HostServiceImplementation implements HostService{
    @Override
    public AccommodationResponse sendEditRequest(int id) {
        return new AccommodationResponse();
    }

    @Override
    public Collection<AccommodationLogDataResponse> getLogsForPeriod(DatePeriod period) {
        return null;
    }

    @Override
    public ReviewResponse reportReview(int reviewId) {
        return null;
    }

    @Override
    public Boolean editAccount(int hostId, AccountEditRequest accountEditRequest) {
        return null;
    }


}
