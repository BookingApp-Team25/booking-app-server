package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;

import java.util.Collection;

public interface AdminService {

    public Collection<ReportedUserResponse> getAllReportedUsers();

    public MessageResponse resolveAccommodationUpdate(int id, int flag);

    public Boolean blockUser(int id);

    public Boolean editAccount(int adminId,AccountEditRequest accountEditRequest);
}
