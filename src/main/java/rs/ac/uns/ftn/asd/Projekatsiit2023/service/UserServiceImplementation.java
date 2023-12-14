package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Guest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Host;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.User;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserRepository;


import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    public Boolean editAccount(int adminId, AccountEditRequest accountEditRequest) {
        return null;
    }
    public Collection<ReportedUserResponse> getAllReportedUsers() {
        return null;
    }
    public Boolean blockUser(int id) {
        return null;
    }

    @Override
    public AccountDetailsResponse getAccountDetails(String username) {
        Optional<User> ret = userRepository.findByUsername(username);
        if(!ret.isEmpty()){
            User user=ret.get();
            AccountDetailsResponse adr=new AccountDetailsResponse(user.getUsername(),user.getFirstName(),user.getLastName(),user.getAddress(),user.getPhoneNumber());
            return adr;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> ret = userRepository.findByUsername(username);
        if (!ret.isEmpty() ) {
            return org.springframework.security.core.userdetails.User.withUsername(username).password(ret.get().getPassword()).roles(ret.get().getRole().toString()).build();
        }
        return null;
    }

    public MessageResponse createAccount(RegistrationRequest registrationRequest){
        UserDetails user=loadUserByUsername(registrationRequest.getUsername());
        if(user==null){
            if(registrationRequest.getPassword().equals(registrationRequest.getPasswordRepeat())){
                PasswordEncoder encoder=new BCryptPasswordEncoder();
                String encodedPassword= encoder.encode(registrationRequest.getPassword());
                if(registrationRequest.getRole().equals(Role.Guest)){
                    Guest guest=new Guest(registrationRequest.getUsername(), encodedPassword, registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),registrationRequest.getAddress(),registrationRequest.getPhoneNumber(),Role.Guest);
                    userRepository.save(guest);
                } else {
                    Host host=new Host(registrationRequest.getUsername(), encodedPassword, registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),registrationRequest.getAddress(),registrationRequest.getPhoneNumber(),Role.Host);
                    userRepository.save(host);
                }
                return new MessageResponse(true,"Account successfully created");
            }
        }
        return new MessageResponse(false,"User already exists");
    }
}
