package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;

import java.util.Collection;

public interface UserService {
    public Boolean editAccount(int adminId, AccountEditRequest accountEditRequest);
    public Collection<ReportedUserResponse> getAllReportedUsers();

    public Boolean blockUser(int id);
}
