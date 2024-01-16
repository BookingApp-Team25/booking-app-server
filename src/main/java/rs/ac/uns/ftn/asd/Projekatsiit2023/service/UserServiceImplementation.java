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

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    public MessageResponse editAccount(String username, AccountEditRequest accountEditRequest) {
        Optional<User> ret=userRepository.findByUsername(username);
        if(!ret.isEmpty()){
            User user=ret.get();
            if(!accountEditRequest.getPassword().isEmpty()){
                if(accountEditRequest.getPassword().equals(accountEditRequest.getPasswordRepeat())){
                    PasswordEncoder encoder=new BCryptPasswordEncoder();
                    String encodedPassword=encoder.encode(accountEditRequest.getPassword());
                    user.setPassword(encodedPassword);
                }
            }
            user.setFirstName(accountEditRequest.getFirstName());
            user.setLastName(accountEditRequest.getLastName());
            user.setAddress(accountEditRequest.getAddress());
            user.setPhoneNumber(accountEditRequest.getPhoneNumber());
            userRepository.save(user);
            return(new MessageResponse(true,"Account is successfully updated"));
        }
        return (new MessageResponse(false,"Error: User doesn't exist"));
    }
    public Collection<ReportedUserResponse> getAllReportedUsers() {
        return null;
    }
  
    public Boolean blockUser(int id) {
        return null;
    }

    public Collection<User> findAll() {
        Collection<User> users = userRepository.findAll();
        return users;
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
    public MessageResponse activateAccount(String code) {
        UUID activationCode=UUID.fromString(code);
        Optional<User> ret=userRepository.findByActivationCode(activationCode);
        if(!ret.isEmpty()){
            User user=ret.get();
            if(user.getActivationTime().plusHours(24).isAfter(LocalDateTime.now())){
                user.setBlocked(false);
                userRepository.save(user);
                return new MessageResponse(true,"ACCOUNT IS SUCCESSFULLY ACTIVATED");
            }
            else {
                userRepository.delete(user);
                return new MessageResponse(false,"ACTIVATION LINK HAS EXPIRED");
            }
        }
        return new MessageResponse(false,"ACCOUNT NOT FOUND");
    }

    @Override
    public MessageResponse deleteAccount(String username) {
        Optional<User> ret=userRepository.findByUsername(username);
        if(ret.isPresent()){
            userRepository.delete(ret.get());
            return new MessageResponse(true,"Account is successfully deleted");
        }
        return new MessageResponse(false,"User not found");
    }

    @Override
    public Boolean isBlocked(String username) {
        Optional<User> ret=userRepository.findByUsername(username);
        if(ret.isPresent()){
            User user=ret.get();
            if(user.getBlocked()){
                return true;
            }
            return false;
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

    public String createAccount(RegistrationRequest registrationRequest){
        UserDetails user=loadUserByUsername(registrationRequest.getUsername());
        if(user==null){
            if(registrationRequest.getPassword().equals(registrationRequest.getPasswordRepeat())){
                PasswordEncoder encoder=new BCryptPasswordEncoder();
                String encodedPassword= encoder.encode(registrationRequest.getPassword());
                if(registrationRequest.getRole().equals(Role.Guest)){
                    Guest guest=new Guest(registrationRequest.getUsername(), encodedPassword, registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),registrationRequest.getAddress(),registrationRequest.getPhoneNumber(),Role.Guest);
                    userRepository.save(guest);
                    return guest.getActivationCode().toString();
                } else {
                    Host host=new Host(registrationRequest.getUsername(), encodedPassword, registrationRequest.getFirstName(),
                            registrationRequest.getLastName(),registrationRequest.getAddress(),registrationRequest.getPhoneNumber(),Role.Host);
                    userRepository.save(host);
                    return  host.getActivationCode().toString();
                }

            }
        }
        return null;
    }

    public HostData getHostById(UUID hostId) {
        User user = userRepository.findUserById(hostId);
        return mapToHostData(user);
    }

    @Override
    public GuestData getGuestByUsername(String username) {
        User guest = userRepository.findGuestByUsername(username);
        return mapToGuestData(guest);
    }

    public HostData mapToHostData(User user) {
        return new HostData(
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public GuestData mapToGuestData(User user) {
        return new GuestData(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
