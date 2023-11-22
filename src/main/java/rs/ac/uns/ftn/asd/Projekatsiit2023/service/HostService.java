package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationResponse;

import java.util.Collection;

public interface HostService {

    public AccommodationResponse sendEditRequest(int id);
}
