package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class HostServiceImplementation implements HostService{
    @Override
    public AccommodationResponse sendEditRequest(int id) {
        return new AccommodationResponse();
    }
}
