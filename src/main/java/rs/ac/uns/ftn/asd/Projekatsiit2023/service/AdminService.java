package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccommodationUpdateResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;

import java.util.Collection;
import java.util.UUID;

public interface AdminService {

    public Collection<ReportedUserResponse> getAllReportedUsers();

    public AccommodationUpdateResponse resolveAccommodationUpdate(int id,int flag);

    public UUID blockUser(int id);

    public Boolean editAccount(int adminId,AccountEditRequest accountEditRequest);
}
