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
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.HostRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserReportRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    HostRepository hostRepository;
    @Autowired
    UserReportRepository userReportRepository;
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
            AccountDetailsResponse adr=new AccountDetailsResponse(user.getId().toString(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getAddress(),user.getPhoneNumber());
            return adr;
        }
        return null;
    }

    @Override
    public AccountDetailsResponse getHostDetails(UUID hostId) {
        Host user= hostRepository.getReferenceById(hostId);
        if(user!=null){
            AccountDetailsResponse adr=new AccountDetailsResponse(user.getId().toString(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getAddress(),user.getPhoneNumber(),user.getRating());
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

    public MessageResponse report(String username,String reason){
        Optional<User> ret=userRepository.findByUsername(username);
        if(ret.isPresent()){
            UserReport userReport=new UserReport(ret.get(),reason);
            userReportRepository.save(userReport);
            return new MessageResponse(true,"User is successfully reported");
        }
        return new MessageResponse(false,"User not found");
    }

    @Override
    public Boolean checkReportPermission(String guestUsername,String hostUsername) {
        Guest guest= (Guest) userRepository.findByUsername(guestUsername).get();
        Host host= (Host) userRepository.findByUsername(hostUsername).get();
        Collection<Reservation> reservations= reservationRepository.findAllHostReservations(host.getId());
        for(Reservation reservation: reservations){
            if(reservation.getGuest().getId()==guest.getId() && reservation.isFinished()){
                return true;
            }
        }
        return false;
    }
}
