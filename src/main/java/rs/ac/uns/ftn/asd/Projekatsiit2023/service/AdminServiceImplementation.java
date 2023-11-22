package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationUpdateResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;

import java.util.Collection;
import java.util.UUID;

@Service
public class AdminServiceImplementation implements AdminService{
    @Override
    public Collection<ReportedUserResponse> getAllReportedUsers() {
        return null;
    }

    @Override
    public AccommodationUpdateResponse resolveAccommodationUpdate(int id, int flag) {
        return null;
    }

    @Override
    public UUID blockUser(int id) {
        return null;
    }
}
