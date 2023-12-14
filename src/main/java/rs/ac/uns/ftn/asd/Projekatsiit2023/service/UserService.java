package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;

import java.util.Collection;

public interface UserService extends UserDetailsService {
    public Boolean editAccount(int adminId, AccountEditRequest accountEditRequest);
    public Collection<ReportedUserResponse> getAllReportedUsers();
    public MessageResponse createAccount(RegistrationRequest registrationRequest);
    public Boolean blockUser(int id);
    public AccountDetailsResponse getAccountDetails(String username);
}
