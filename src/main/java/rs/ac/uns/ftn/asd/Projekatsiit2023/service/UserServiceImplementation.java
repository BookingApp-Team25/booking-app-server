package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;

import java.util.Collection;

@Service
public class UserServiceImplementation implements UserService {
    @Override
    public Boolean editAccount(int adminId, AccountEditRequest accountEditRequest) {
        return null;
    }
    @Override
    public Collection<ReportedUserResponse> getAllReportedUsers() {
        return null;
    }

    @Override
    public Boolean blockUser(int id) {
        return null;
    }
}
