package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.HostRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.UserRepository;

@Service
public class HostServiceImplementation implements HostService{
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public AccommodationResponse sendEditRequest(int id) {
        return new AccommodationResponse();
    }

    @Override
    public Collection<AccommodationLogDataResponse> getLogsForPeriod(DatePeriod period) {
        return null;
    }


    @Override
    public Boolean editAccount(int hostId, AccountEditRequest accountEditRequest) {
        return null;
    }

    @Override
    public Collection<AccountDetailsResponse> getGuestsForHost(String hostUsername) {
        Host host = (Host) userRepository.findByUsername(hostUsername).get();
        List<Reservation> reservations=reservationRepository.findAllByHostId(host.getId());
        Collection<AccountDetailsResponse> accountDetailsResponses=new ArrayList<AccountDetailsResponse>();
        for(Reservation reservation : reservations){
            User user=userRepository.getReferenceById(reservation.getGuestId());
            AccountDetailsResponse adr=new AccountDetailsResponse(user.getId().toString(),user.getUsername(),user.getFirstName(),
                    user.getLastName(),user.getAddress(),user.getPhoneNumber());
            accountDetailsResponses.add(adr);
        }
        return accountDetailsResponses;
    }

    public Collection<Host> findAll() {
        Collection<Host> hosts = hostRepository.findAll();
        return hosts;
    }
}
