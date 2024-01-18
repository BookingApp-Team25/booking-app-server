package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;

import java.util.Collection;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    public MessageResponse editAccount(String username, AccountEditRequest accountEditRequest);
    public Collection<UserReportResponse> getAllUserReports();
    public UserResponse getUserById(String userId);
    public String createAccount(RegistrationRequest registrationRequest);
    public Boolean blockUser(UUID userId);
    public AccountDetailsResponse getAccountDetails(String username);
    public MessageResponse activateAccount(String code);
    public MessageResponse deleteAccount(String username);
    public Boolean isBlocked(String username);
    public HostData getHostById(UUID hostId);
    public GuestData getGuestByUsername(String username);
    public AccountDetailsResponse getHostDetails(UUID hostId);
    public MessageResponse report(String username,String reason);
    public Boolean checkReportPermission(String guestUsername,String hostUsername);
}
