package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.UserRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.AccountEditRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReportedUserResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;

import java.util.Collection;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Collection<User> findAll() {
        Collection<User> users = userRepository.findAll();
        return users;
    }

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
